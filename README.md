#

# USER MANAGEMENT APP

In this project, we'll start trying to manage users in an app. The API will have different roles like STANDARD and ADMIN; there will be methods that can only be used by ADMIN and also in some cases we'll need to verify if the user is the same one as the one they want to view. 

Feel free to give me any feedback! 

# BEST PRACTICES IN THIS PROJECT

In our previous projects, we have documentation on the README of my learning and best practices. Here, I'll document new things I learn or apply on this project. 

## ID ASSIGNMENT IN REPOSITORY. 

we do this because the creation from the constructor would not be ideal, even if we're using DDD our best option would be receiving the id from the repository using the Factory pattern. 

Since I don't know or dive into patterns yet, I'm doing an easier version by passing the id through a setter to the model. This method can only be used internally since we'll be using DTOs. 
