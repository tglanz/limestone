select *
from users
left join cities on users.city_id = cities.id
where age > 20