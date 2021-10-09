package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class JsonCustomClassDeserializer {

//    class Client7Deserializer(private val clazz: Class<T>) : StdDeserializer<Client7>(clazz) {
//
//        override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Client7 {
//            val value = p?.codec?.readValue(p, clazz)
//            return if (value == null) Client7.empty() else Client7.just(value)
//        }
//    }

//    fun deserialize(parser: JsonParser, deserializer: DeserializationContext?): Client7? {
//        val codec = parser.codec
//        val node = codec.readTree<JsonNode>(parser)
//
//        val clientSplit = node["client"].toString().split(" ")
//        return Client7(clientSplit[0], clientSplit[1], clientSplit[2])
//    }

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""

        val module = SimpleModule("CustomCarDeserializer", Version(1, 0, 0, null, null, null))
        module.addDeserializer(Client7::class.java, CustomClient7Deserializer())

        val objectMapper = ObjectMapper()
            .registerModules(KotlinModule(), JavaTimeModule(), module)

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}

// Пример брал отсюда, п. 4.2: https://www.baeldung.com/jackson-object-mapper-tutorial
// Корректно ли написана конструкция "@JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Client7?>(vc)" - не знаю. Но оно работает
class CustomClient7Deserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Client7?>(vc) {
    override fun deserialize(parser: JsonParser, deserializer: DeserializationContext): Client7 {
        val codec = parser.codec
        val node = codec.readTree<JsonNode>(parser)

        val clientSplit = node["client"].toString().replace("\"", "").trim().split(" ")
        return Client7(clientSplit[1], clientSplit[0], clientSplit[2])
    }
}