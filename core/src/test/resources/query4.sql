select cities.name, avg(users.age)
from users
left join cities on users.city_id = cities.id
where age > 20
group by cities.name