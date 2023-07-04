package features.sons_of_officers.presentation.sons_of_officers

data class FilterState(
    val libyaId:String = "",
    val fileNumber:String="",
    val educationLevel:String="",
    val city:String="",
    val fileState:String="",
    val referralForTraining:String="",
){
    fun getFilterStateVariablesNamesAndValues():Map<String,String>{
        val variables = this.javaClass.declaredFields
        val values = variables.map { field ->
            field.isAccessible = true
            field.get(this)
        }
        val variableNames = variables.map { it.name }
        return variableNames.zip(values).associate { it.first to it.second.toString() }
    }
}