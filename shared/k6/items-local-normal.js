import http from 'k6/http';
import { sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export let options = {
  scenarios: {
    normal: {
      exec: "normal",
      executor: 'constant-arrival-rate',
      rate: 300,
      timeUnit: '1s',
      duration: '60s',
      preAllocatedVUs: 100,
      maxVUs: 400,
    }
  }
};

const host = "http://coupon-app:8080";

export function normal() {
  let itemCount = randomIntBetween(1, 5000);
  let item_ids = [];
  for (let i = 0; i < itemCount; i++) {
      item_ids.push(`ITEM${randomIntBetween(1, 5000)}`);
  }

  let amount = randomIntBetween(1, 25000);

  let data = { item_ids, amount };

  http.post(host + '/coupon', JSON.stringify(data), {
    headers: { 'Content-Type': 'application/json' },
  });
}