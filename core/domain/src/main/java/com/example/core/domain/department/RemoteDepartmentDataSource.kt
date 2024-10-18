package com.example.core.domain.department

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result

interface RemoteDepartmentDataSource {
    suspend fun getDepartments(): Result<List<Department>, DataError.Network>
}