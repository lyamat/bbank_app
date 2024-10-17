package com.example.bbank.domain.usecase.remote

//import com.example.bbank.domain.networking.Result
//import com.example.bbank.domain.networking.safeCall
//import retrofit2.Response
//
//abstract class BaseRemoteUseCase<T, R>(
//    private val repositoryCall: suspend () -> Response<List<T>>,
//    private val mapper: (T) -> R
//) {
//    suspend operator fun invoke(): Result<List<R>, DataError.Network> {
//        val result = safeCall { repositoryCall() }
//        return when (result) {
//            is Result.Success -> {
//                val mappedList = result.data.map { mapper(it) }
//                Result.Success(mappedList)
//            }
//
//            is Result.Error -> {
//                Result.Error(result.error)
//            }
//        }
//    }
//}

