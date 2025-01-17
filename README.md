# GitHub REST API Client

This project is a GitHub REST API client implemented in Java using Spring Boot, Spring WebFlux, Spring Boot Caching, Redis, and WebTestClient for integration testing.

The GitHub REST API Client is a simple application that allows users to retrieve information abou GitHub repositories and their branches using the GitHub API.

# Usage

Endpoints:
- `/github/repos/{username}`: Retrieves a list of all repositories, which are not forks for the specified GitHub user. And handle exception if user not found.

The application uses properties defined in the `application.properties` file for configuration. You can specify your GitHub access token and other properties in this file.
