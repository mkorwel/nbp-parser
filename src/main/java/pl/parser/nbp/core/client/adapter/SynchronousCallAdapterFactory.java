package pl.parser.nbp.core.client.adapter;

import pl.parser.nbp.core.client.exception.HttpException;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by mateusz on 30/01/16.
 */
public class SynchronousCallAdapterFactory implements CallAdapter.Factory {
	public static CallAdapter.Factory create() {
		return new SynchronousCallAdapterFactory();
	}

	@Override
	public CallAdapter<Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
		if (isCallRequest(returnType)) {
			return null;
		}

		return new CallAdapter<Object>() {
			@Override
			public Type responseType() {
				return returnType;
			}

			@Override
			public <R> Object adapt(Call<R> call) {
				try {
					return call.execute().body();
				} catch (IOException e) {
					throw new HttpException();
				}
			}
		};
	}

	private boolean isCallRequest(Type returnType) {
		return returnType.getTypeName().contains("retrofit2.Call");
	}
}
