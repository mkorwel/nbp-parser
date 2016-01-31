package pl.parser.nbp.core.client.adapter

import retrofit2.CallAdapter
import spock.lang.Specification

import java.lang.reflect.Type

/**
 * Created by mateusz on 31/01/16.
 */
class SynchronousCallAdapterFactorySpec extends Specification {
    SynchronousCallAdapterFactory adapter
    Type type

    def setup() {
        adapter = SynchronousCallAdapterFactory.create()
        type = Mock(Type)
    }

    def "should return null adapter when type is retrofit2.Call"() {
        given:
        type.getTypeName() >> "retrofit2.Call"

        when:
        CallAdapter result = adapter.get(type, null, null)

        then:
        result == null
    }

    def "should return adapter when type is not retrofit2.Call"() {
        given:
        type.getTypeName() >> "Object"

        when:
        CallAdapter result = adapter.get(type, null, null)

        then:
        result != null
    }
}
