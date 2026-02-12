#

# USER MANAGEMENT APP

In this project, we'll start trying to manage users in an app. The API will have different roles like STANDARD and ADMIN; there will be methods that can only be used by ADMIN and also in some cases we'll need to verify if the user is the same one as the one they want to view. 

Feel free to give me any feedback! 

# BEST PRACTICES IN THIS PROJECT

In our previous projects, we have documentation on the README of my learning and best practices. Here, I'll document new things I learn or apply on this project. 

## ID ASSIGNMENT IN REPOSITORY. 

we do this because the creation from the constructor would not be ideal, even if we're using DDD our best option would be receiving the id from the repository using the Factory pattern. 

Since I don't know or dive into patterns yet, I'm doing an easier version by passing the id through a setter to the model. This method can only be used internally since we'll be using DTOs. 


## DTOs with no setters//IMMUTABILITY

Jackson works without the settern even when using the no-arg corstructor. Howewer, it would be better if we configucred it properly for that or used java records. 

The decision is because we want to make our DTOs immutable to prevent bugs from accidental mutation. They also align with functional programming principles. 

**Why Remove Setters from DTOs**:
- Immutability - Once created, the DTO can't be accidentally modified
- Thread Safety - Immutable objects are inherently thread-safe
- Clearer Intent - DTOs are meant to transfer data, not be modified
- Fewer Bugs - Can't accidentally mutate data in transit

Yes, DTOs should generally be immutable. The primary purpose of a Data Transfer Object (DTO) is to reliably carry data between different application layers or services, and immutability helps ensure that the data remains consistent and is not accidentally modified during transit. 

## DDD in User. 

Currently, we have an anemic model, our User is just a POJO with no behavior, and it doesn't have any behavior, DDD and OOP suggest that our domain model contains logic to keep their invariants and information that concerns the model. Since we don't work yet with design partterns, we'll keep it simple with a setter that uses a guard. 

## BREAKING DOWN THE SERVICE 

We create other classes like **Validator and mapper** to have this logic separated since the Service is doing too much by mapping and validating all in a same class, violating SRP. 

## NOT USING VALIDATOR.VALIDATECREATEUSER
We removed this line because our model domain already does this on the constructor so we just need to validate the whole DTO. 

We could keep them both for added security, it's acceptable but redundant. 

> **Why Keep the Other Methods?**

The validateCreateUser() method was doing something that the constructor is already doing in our User
#### validatePasswordUpdate()
 - Validates the DTO structure (null checks, both passwords present). The User.updatePassword() only validates the new password itself.

#### validateUpdate()
 - Validates the DTO structure (at least one field present, name not blank if provided). This is DTO-level validation, not domain validation.

#### validateId()
 - Validates IDs before fetching users. This is a service-layer concern.

#### The Pattern:
Domain validation (business rules) → Lives in Userclass
DTO structure validation (null checks, "at least one field") → Lives in 
UserValidator
ID validation (technical validation) → Lives in 
UserValidator

## REMOVAL OF CAN...USER() ON USERAUTHORIZATION
This was a call made because at the moment we applied DDD and let the model have its own behavior, the code used here duplicated. We have methods in our User class like 
```
    User.isAdmin();
    User.canView();
    User.canModify();

```
They had the same behavior.


# Through this refactoring, you've learned:

**Rich Domain Models** - Objects should have behavior, not just data
**Immutability - Final** fields prevent bugs and improve thread safety
**Single Responsibility** - Each class should do one thing well
**Encapsulation** - Validation belongs with the data
**Defensive Programming** - Protect your data structures
**Clean Architecture** - Proper layer separation


# NEXT STEPS: 

Master testing - Write tests for everything
Learn Spring Security - Proper authentication/authorization
Study design patterns - Factory, Strategy, Builder, etc.
