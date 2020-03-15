select count(*), store.s_store_name
from store_sales
left join store on
    store_sales.ss_store_sk =  store.s_store_sk
group by store.s_store_name
order by count(*), store.s_store_name
limit 100;
