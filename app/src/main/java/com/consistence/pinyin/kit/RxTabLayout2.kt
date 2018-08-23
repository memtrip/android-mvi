package com.consistence.pinyin.kit

import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent
import io.reactivex.Observable
import io.reactivex.functions.Consumer

class RxTabLayout2 private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {

        fun selectionEvents(view: TabLayout): Observable<TabLayoutSelectionEvent> {
            return TabLayoutSelectionEventObservable2(view)
        }

        fun select(view: TabLayout): Consumer<in Int> {
            return Consumer { index ->
                if (index < 0 || index >= view.getTabCount()) {
                    throw IllegalArgumentException("No tab for index " + index!!)
                }

                view.getTabAt(index)!!.select()
            }
        }
    }
}
