select *
from store_sales
left join store on
    store_sales.ss_store_sk =  store.s_store_sk
limit 100;
