//package com.example.bbank.data.repositories.remote
//
//import com.example.bbank.data.remote.BelarusBankApi
//import com.example.bbank.data.remote.dto.DepartmentResponseDto
//import retrofit2.Response
//import javax.inject.Inject
//
//class DepartmentRemoteImpl @Inject constructor(
//    private val belarusBankApi: BelarusBankApi
//) : DepartmentRemote {
//    override suspend fun getRemoteDepartmentsByCity(city: String): Response<List<DepartmentResponseDto>> =
//        belarusBankApi.getDepartmentsByCity(city)
//}