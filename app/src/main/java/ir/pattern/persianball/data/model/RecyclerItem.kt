package ir.pattern.persianball.data.model

import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import java.io.Serializable


class RecyclerItem : Serializable {
	var viewType = 0
	lateinit var data: PersianBallRecyclerData

	constructor() {}
	constructor(viewType: Int, data: PersianBallRecyclerData) {
		this.viewType = viewType
		this.data = data
	}

	constructor(data: PersianBallRecyclerData) {
		this.data = data
		viewType = data.viewType
	}
}