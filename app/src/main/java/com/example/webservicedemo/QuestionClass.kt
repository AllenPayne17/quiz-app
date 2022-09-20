package com.example.webservicedemo

data class QuestionClass(val id : Int?, val question: String?,val optionA: String?,val optionB: String?,val optionC: String?,
                         val optionD: String?,
                         val answer: String?,
                         val description: String?) {

    constructor(question: String?, answer: String?) :
            this (-1, question,null,null,null,null,answer,null){
    }
    constructor(question: String?,optionA: String?, optionB: String?,optionC: String?,optionD: String?)
            : this (-1,question,optionA,optionB,optionC,optionD,null,null){
    }

}