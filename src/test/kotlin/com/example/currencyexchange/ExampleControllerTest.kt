package com.example.currencyexchange

class ExampleControllerTest: FunSpec() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    init {
        extension(SpringExtension)

        beforeTest {
            userRepository.save(User(1, "John Doe"))
        }

        test("Get /users/{id} should return the user") {
            mockMvc.get("/users/1").andExpect {
                status { isOk() }
                jsonPath("\$.id") { value(1) }
                jsonPath("\$.name") { value("John Doe") }
            }.andReturn()
        }
    }
} {
}