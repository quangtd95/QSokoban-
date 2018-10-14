package com.quangtd.qsokoban.common

interface AsyncTaskCallback<ProgressType, CompleteType> {
        fun onPreExecute()
        fun onCompleteExecute(result: CompleteType)
        fun onProgress(progress: ProgressType)
    }