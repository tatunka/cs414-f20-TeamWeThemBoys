package com.xgame.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xgame.data.entities.Test;

public interface TestRepository extends JpaRepository<Test, Integer> {

}

/*
Example 2.3. Query creation from method names

public interface UserRepository extends Repository<User, Long> {

  List<User> findByEmailAddressAndLastname(String emailAddress, String lastname);
}
We will create a query using the JPA criteria API from this but essentially this translates into the following query:

select u from User u where u.emailAddress = ?1 and u.lastname = ?2
Spring Data JPA will do a property check and traverse nested properties as described in ???. Here's an overview of the keywords supported for JPA and what a method containing that keyword essentially translates to.



Table 2.2. Supported keywords inside method names

Keyword	Sample	JPQL snippet
And					findByLastnameAndFirstname			… where x.lastname = ?1 and x.firstname = ?2
Or					findByLastnameOrFirstname			… where x.lastname = ?1 or x.firstname = ?2
Between				findByStartDateBetween				… where x.startDate between 1? and ?2
LessThan			findByAgeLessThan					… where x.age < ?1
GreaterThan			findByAgeGreaterThan				… where x.age > ?1
After				findByStartDateAfter				… where x.startDate > ?1
Before				findByStartDateBefore				… where x.startDate < ?1
IsNull				findByAgeIsNull						… where x.age is null
IsNotNull,NotNull	findByAge(Is)NotNull				… where x.age not null
Like				findByFirstnameLike					… where x.firstname like ?1
NotLike				findByFirstnameNotLike				… where x.firstname not like ?1
StartingWith		findByFirstnameStartingWith			… where x.firstname like ?1 (parameter bound with appended %)
EndingWith			findByFirstnameEndingWith			… where x.firstname like ?1 (parameter bound with prepended %)
Containing			findByFirstnameContaining			… where x.firstname like ?1 (parameter bound wrapped in %)
OrderBy				findByAgeOrderByLastnameDesc		… where x.age = ?1 order by x.lastname desc
Not					findByLastnameNot					… where x.lastname <> ?1
In					findByAgeIn(Collection<Age> ages)	… where x.age in ?1
NotIn				findByAgeNotIn(Collection<Age> age)	… where x.age not in ?1
True				findByActiveTrue()					… where x.active = true
False				findByActiveFalse()					… where x.active = false
*/