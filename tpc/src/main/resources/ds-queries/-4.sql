select count(*), store.s_city, store.s_store_name
from store
group by store.s_city, store.s_store_name
