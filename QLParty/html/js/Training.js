function TaskData(){
	this.tId = -1;
	this.tName = "";
	this.tRemarks = "";
	this.tStartDate = "";
	this.tEndDate = "";
	this.trainingArray = new Array();
	
	TaskData.prototype.setInfo = TaskData_setInfo;
	TaskData.prototype.setTrainingInfo = TaskData_setTrainingInfo;
	TaskData.prototype.setTplId = TaskData_setTplId;
	TaskData.prototype.addTraining = TaskData_addTraining;
	TaskData.prototype.deleteTraining = TaskData_deleteTraining;
	TaskData.prototype.getTraining = TaskData_getTraining;
	TaskData.prototype.clear = TaskData_clear;
	TaskData.prototype.isEmpty = TaskData_isEmpty;
	TaskData.prototype.getCount = TaskData_getCount;
	TaskData.prototype.toJson = TaskData_toJson;
}

function TaskData_setInfo(pName,pRemarks){
	this.tName = pName;
	this.tRemarks = pRemarks;
}

function TaskData_setTrainingInfo(pStart,pEnd,pRemarks){
	this.tStartDate = pStart;
	this.tEndDate = pEnd;
	this.tRemarks = pRemarks;
}

function TaskData_setTplId(pTplId){
	this.tId = pTplId;
}

function TaskData_addTraining(pTraining){
	this.trainingArray[this.trainingArray.length] = pTraining;
}

function TaskData_deleteTraining(pTgId,pSeq){
	var index = -1;
	for(var i=0;i<this.trainingArray.length;i++){
		if(this.trainingArray[i].getTgId() == pTgId
				&& this.trainingArray[i].getSeq() == pSeq){
			index = i;
			break;
		}
	}
	if(index == -1)
		return;
	this.trainingArray.splice(index,1);
}

function TaskData_getTraining(pTgId,pSeq){
	var index = -1;
	for(var i=0;i<this.trainingArray.length;i++){
		if(this.trainingArray[i].getTgId() == pTgId
				&& this.trainingArray[i].getSeq() == pSeq){
			return this.trainingArray[i];
		}
	}
}

function TaskData_isEmpty(){
	var length = this.trainingArray.length;
	if(length == 0)
		return true;
	var count = 0;
	for(var i=0;i<this.trainingArray.length;i++){
		if(this.trainingArray[i].isDeleted()){
			count++;
		}
	}
	return length == count;
}

function TaskData_getCount(){
	return this.trainingArray.length;
}

function TaskData_clear(){
	this.tName = null;
	this.trainingArray = new Array();
}

function TaskData_toJson(){
	var json = "{\"Id\" : " + this.tId + ",\"Name\" : \"" + this.tName + "\",\"Remarks\" : \"" + this.tRemarks + "\",\"StartDate\" : \"" + this.tStartDate + "\",\"EndDate\" : \"" + this.tEndDate + "\", \"Trainings\" : [";
	for(var i=0;i<this.trainingArray.length;i++){
		if(i > 0)
			json += ",";
		json += this.trainingArray[i].toJson();
	}
	json += "]}";
	return json;
}

function TrainingData(pId,pSeq){
	this.tgJson = {
			_RelateId : 0,
			_IsDlt : 0,
			ID_TrainingGame : pId,
			SortId : pSeq,
			TrainingTimes : 10,
			TrainingLevel : 1,
			Rest : 30,
			Remarks : ""
		};

	TrainingData.prototype.getTgId = TrainingData_getTgId;
	TrainingData.prototype.getSeq = TrainingData_getSeq;
	TrainingData.prototype.setRelateId = TrainingData_setRelateId;
	TrainingData.prototype.setInfo = TrainingData_setInfo;
	TrainingData.prototype.del = TrainingData_delete;
	TrainingData.prototype.isDeleted = TrainingData_isDeleted;
	TrainingData.prototype.toJson = TrainingData_toJson;
}

function TrainingData_getTgId(){
	return this.tgJson.ID_TrainingGame;
}

function TrainingData_getSeq(){
	return this.tgJson.SortId;
}

function TrainingData_setInfo(times,level,rest,remarks){
	this.tgJson.TrainingTimes = times;
	this.tgJson.TrainingLevel = level;
	this.tgJson.Rest = rest;
	this.tgJson.Remarks = remarks;
}

function TrainingData_setRelateId(pRelateId){
	this.tgJson._RelateId = pRelateId;
}

function TrainingData_delete(){
	this.tgJson._IsDlt = 1;
}

function TrainingData_isDeleted(){
	return this.tgJson._IsDlt == 1;
}

function TrainingData_toJson(){
	return JSON.stringify(this.tgJson); 
}