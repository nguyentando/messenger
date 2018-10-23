package com.donguyen.domain.util

import io.reactivex.ObservableTransformer

/**
 * Created by DoNguyen on 23/10/18.
 */
abstract class Transformer<T> : ObservableTransformer<T, T>