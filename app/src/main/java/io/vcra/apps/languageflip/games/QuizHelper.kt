package io.vcra.apps.languageflip.games

class QuizHelper(val ph: Int) {

    var currentQuestion = -1
    var currentAnswer = 0
    var correctAnswer = 0
    var currentScore = 0



    var qq: QuizQuestion = QuizQuestion()



    fun genQQ(): QuizQuestion {
        qq = QuizQuestion()
        qq.question =  "What is todays date?"
        qq.answers[0] = "test1"
        qq.answers[1] = "test2"
        qq.answers[2] = "test3"
        qq.answers[3] = "test4"
        return qq
    }

    fun getAnswer(): String{
        currentQuestion++
        return qq.answers[currentQuestion]
    }

    fun getQuestion(): String{
        return qq.question
    }

    fun nextQuestion(){
        currentQuestion++
        currentAnswer = -1
    }




}