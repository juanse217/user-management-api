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

## @RequestHeader instead of @RequestParam
The first one extracts the information from the headers of the HTTP request (metadata). This makes that the information sent through it, is not saved in the history since it's not in the URL. 

@RequestParam is used for pagination or filter/search, while @RequestHeader works for authentication and passing metadata. 

There are certain conventions: 

**STANDARD IN ALL HTTP REQUESTS:** 
- Authorization - Authentication tokens
- Content-Type - Body format
- Accept - Response format
- User-Agent - Client info

**CUSTOM HEADERS:**
The convention is that they start with X-
- X-User-id
- X-API-Key
- X-Custom-Auth




# Through this refactoring, you've learned:

**Rich Domain Models** - Objects should have behavior, not just data
**Immutability - Final** fields prevent bugs and improve thread safety
**Single Responsibility** - Each class should do one thing well
**Encapsulation** - Validation belongs with the data
**Defensive Programming** - Protect your data structures
**Clean Architecture** - Proper layer separation


# 🎯 NEXT STEPS: ROADMAP TO MID-LEVEL

## **Phase 1: Testing (Priority 1 - Start Here)**

### Week 1-2: Core Testing
1. **Unit Tests**
   - `User` domain model (validation, behavior methods)
   - `UserValidator` (all validation methods)
   - `UserMapper` (DTO conversions)
   - `UserAuthorization` (authorization logic)

2. **Service Layer Tests (with Mockito)**
   - `UserService` - mock repository, auth, validator, mapper
   - Test all CRUD operations
   - Test authorization scenarios

3. **Integration Tests**
   - `UserController` with `@WebMvcTest` or `@SpringBootTest`
   - Test all endpoints with MockMvc
   - Test error scenarios

**Goal:** 70%+ code coverage

**Resources:**
- [Baeldung JUnit 5 Guide](https://www.baeldung.com/junit-5)
- [Mockito Official Docs](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing Guide](https://spring.io/guides/gs/testing-web)

---

## **Phase 2: Exception Handling (Priority 2)**

### Week 2: Global Exception Handler
1. Create `@RestControllerAdvice` class
2. Handle `UserNotFoundException` → 404
3. Handle `UnauthorizedException` → 403
4. Handle `IllegalArgumentException` → 400
5. Handle generic `Exception` → 500
6. Return consistent error response DTO

**Resources:**
- [Baeldung Exception Handling](https://www.baeldung.com/exception-handling-for-rest-with-spring)

---

## **Phase 3: Bean Validation (Priority 3)**

### Week 3: Add Validation Annotations
1. Add dependency: `spring-boot-starter-validation`
2. Annotate DTOs (`@NotNull`, `@NotBlank`, `@Size`, `@Min`)
3. Add `@Valid` to controller methods
4. Remove manual validation from `UserValidator`
5. Keep domain validation in `User` class

**Resources:**
- [Baeldung Bean Validation](https://www.baeldung.com/javax-validation)

---

## **Phase 4: Spring Security (Priority 4)**

### Week 3-4: JWT Authentication
1. Add Spring Security dependency
2. Implement password hashing (BCrypt)
3. Create login endpoint (returns JWT)
4. Create JWT utility class (generate/validate tokens)
5. Create JWT filter (validate tokens on requests)
6. Replace `X-User-Id` header with `Authorization: Bearer <token>`
7. Add method security (`@PreAuthorize`)

**Resources:**
- [Spring Security Official Docs](https://docs.spring.io/spring-security/reference/index.html)
- [Baeldung Spring Security JWT](https://www.baeldung.com/spring-security-oauth-jwt)

---

## **Phase 5: Design Patterns (Priority 5)**

### Week 4-5: Implement Patterns
1. **Factory Pattern** - `UserFactory` for creating users
2. **Strategy Pattern** - Different authorization strategies
3. **Builder Pattern** - Complex User creation (optional)

**Resources:**
- [Refactoring Guru](https://refactoring.guru/design-patterns)
- [Baeldung Design Patterns](https://www.baeldung.com/design-patterns-series)

---

## **Phase 6: API Documentation (Priority 6)**

### Week 5: Swagger/OpenAPI
1. Add `springdoc-openapi` dependency
2. Add annotations to controller (`@Operation`, `@ApiResponse`)
3. Configure Swagger UI
4. Document all endpoints

**Resources:**
- [SpringDoc OpenAPI](https://springdoc.org/)

---

## **Phase 7: Pagination & Filtering (Priority 7)**

### Week 6: Advanced Features
1. Add `Pageable` parameter to `GET /api/users`
2. Return `Page<UserDTO>` instead of `List<UserDTO>`
3. Add filtering by role (`?role=ADMIN`)
4. Add sorting (`?sort=name,asc`)

**Resources:**
- [Baeldung Pagination](https://www.baeldung.com/spring-data-jpa-pagination-sorting)

---

## **Recommended Implementation Order**

1. ✅ **Testing** (most important, do first)
2. ✅ **Exception Handling** (quick win, improves testing)
3. ✅ **Bean Validation** (removes boilerplate)
4. ✅ **Spring Security** (critical for real apps)
5. ✅ **Design Patterns** (refactoring exercise)
6. ✅ **API Documentation** (professional touch)
7. ✅ **Pagination/Filtering** (nice-to-have)

**Estimated Timeline:** 6-8 weeks total (learning + implementation)

**Start with Phase 1 (Testing) - it's the foundation for everything else!** 🚀
