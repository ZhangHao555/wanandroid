package com.ahao.wanandroid.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class RxBus {

    companion object {
        private val subject: PublishSubject<Any> = PublishSubject.create()
        fun post(data: Any) = subject.onNext(data)

        fun toObservable(code: String): Observable<Message>? {
            return subject.ofType(Message::class.java)
                    .filter {
                        it.code == code
                    }
        }
    }

}