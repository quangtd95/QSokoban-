package com.quangtd.qsokoban.ui.screen.level

import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.domain.repository.GameDataRepository
import com.quangtd.qsokoban.domain.repository.GameDataRepositoryImpl
import com.quangtd.qsokoban.mvpbase.BasePresenter

/**
 * Created by quang.td95@gmail.com
 * on 10/20/2018.
 */
class LevelPresenter : BasePresenter<LevelView>() {
    private lateinit var repository: GameDataRepository
    private lateinit var levelAdapter: LevelAdapter
    override fun onInit() {
        repository = GameDataRepositoryImpl()
    }

    fun setAdapter(levelAdapter: LevelAdapter) {
        this.levelAdapter = levelAdapter
    }

    fun loadData() {
        val levelList = repository.loadData(getContext()!!)
        levelAdapter.setItems(levelList)
        getIView()?.onLoadedData()
        getIView()?.scrollToCurrentGame(levelList.findLast { it.isUnlock }?.id ?: 1)
    }

    fun chooseLevel(level: Level) {
        if (!level.isUnlock) {
            getIView()?.showAlertLevelNotUnlock()
            return
        }
        getIView()?.startGameScreen(level)
    }


}