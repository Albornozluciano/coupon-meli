package com.mercadolibre.coupon.domain.service.item.implementation;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import com.mercadolibre.coupon.domain.dto.item.GetItemsSpecification;
import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.domain.repository.ItemRepository;
import com.mercadolibre.coupon.domain.service.item.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

  private final ExecutorService executor;

  private final ItemRepository itemRepository;

  public ItemServiceImpl(final ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
    this.executor = Executors.newVirtualThreadPerTaskExecutor();
  }

  /**
   * Retrieves all item details. It uses virtual threads for improving performance keeping their original order.
   *
   * @param getItemsSpecification Specifies the itemIds for which details will be retrieved.
   * @return List of items with their details.
   */
  @Override
  public List<Item> getItems(final GetItemsSpecification getItemsSpecification) {
    final List<String> itemIds = getItemsSpecification.itemIds();
    // Get all items async, using an index for keeping the original order
    return IntStream.range(0, itemIds.size())
        .mapToObj(index -> CompletableFuture.supplyAsync(
            () -> new ItemWrapper(index, itemRepository.getItem(itemIds.get(index))), executor
        ))
        .map(CompletableFuture::join)
        .filter(wrapper -> wrapper.item != null)
        .sorted(Comparator.comparingInt(ItemWrapper::index))
        .map(ItemWrapper::item)
        .toList();
  }

  private record ItemWrapper(int index, Item item) {

  }
}