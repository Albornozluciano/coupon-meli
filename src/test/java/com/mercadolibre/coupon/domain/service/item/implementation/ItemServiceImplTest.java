package com.mercadolibre.coupon.domain.service.item.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mercadolibre.coupon.domain.dto.item.GetItemsSpecification;
import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.domain.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

  @Mock
  private ItemRepository itemRepository;

  @InjectMocks
  private ItemServiceImpl itemService;

  @Test
  void getItems() {
    when(itemRepository.getItem("MLA123")).thenReturn(new Item("MLA123", 1f));
    when(itemRepository.getItem("MLA999")).thenReturn(new Item("MLA999", 99f));
    when(itemRepository.getItem("MLANULL")).thenReturn(null);

    List<Item> items = this.itemService.getItems(new GetItemsSpecification(List.of("MLA123", "MLA999", "MLANULL")));

    assertEquals(List.of(new Item("MLA123", 1f), new Item("MLA999", 99f)), items);
  }
}