package com.example.nestko.core.data

import com.example.nestko.core.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockPropertyRepositoryImpl @Inject constructor() : PropertyRepository {

    private val mockProperties = listOf(
        Property(
            id = "1",
            landlordId = "l1",
            address = Address("IT Park", "Lahug", "Cebu City", "Cebu", "6000"),
            type = PropertyType.CONDO,
            units = listOf(
                Unit("u1", "1", "1201", 1, 1, 35.0, 25000.0),
                Unit("u2", "1", "1202", 2, 2, 55.0, 45000.0)
            ),
            amenities = listOf("WiFi", "Pool", "Gym", "24/7 Security"),
            photos = listOf(
                "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&q=80&w=1000",
                "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&q=80&w=1000"
            ),
            description = "Modern condo in the heart of IT Park. Walking distance to offices and restaurants.",
            rating = 4.8f,
            reviewCount = 12
        ),
        Property(
            id = "2",
            landlordId = "l2",
            address = Address("Escario St.", "Capitol Site", "Cebu City", "Cebu", "6000"),
            type = PropertyType.APARTMENT,
            units = listOf(
                Unit("u3", "2", "A1", 1, 1, 28.0, 15000.0)
            ),
            amenities = listOf("WiFi", "Laundry", "Parking"),
            photos = listOf(
                "https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&q=80&w=1000"
            ),
            description = "Affordable apartment near the Capitol. Very accessible to public transport.",
            rating = 4.5f,
            reviewCount = 8
        ),
        Property(
            id = "3",
            landlordId = "l1",
            address = Address("A.S. Fortuna", "Banilad", "Mandaue City", "Cebu", "6014"),
            type = PropertyType.HOUSE,
            units = listOf(
                Unit("u4", "3", "Main", 3, 2, 120.0, 60000.0)
            ),
            amenities = listOf("Garden", "Garage", "Pet Friendly", "Gate"),
            photos = listOf(
                "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&q=80&w=1000"
            ),
            description = "Spacious family house in a quiet neighborhood in Banilad.",
            rating = 4.9f,
            reviewCount = 5
        ),
        Property(
            id = "4",
            landlordId = "l3",
            address = Address("Punta Engaño", "Mactan", "Lapu-Lapu City", "Cebu", "6015"),
            type = PropertyType.CONDO,
            units = listOf(
                Unit("u5", "4", "808", 1, 1, 40.0, 35000.0)
            ),
            amenities = listOf("Beach Access", "Pool", "Gym", "Concierge"),
            photos = listOf(
                "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&q=80&w=1000"
            ),
            description = "Luxurious beachfront condo with amazing sunset views.",
            rating = 5.0f,
            reviewCount = 20
        )
    )

    override fun getProperties(): Flow<List<Property>> = flowOf(mockProperties)

    override fun getPropertyById(id: String): Flow<Property?> = flowOf(mockProperties.find { it.id == id })

    override fun getFeaturedProperties(): Flow<List<Property>> = flowOf(mockProperties.take(3))

    override fun searchProperties(query: String): Flow<List<Property>> = flowOf(
        mockProperties.filter {
            it.address.fullAddress().contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    )
}
