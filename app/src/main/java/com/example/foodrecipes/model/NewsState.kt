package com.example.foodrecipes.model


/*注意密封类在此处的优点 获取到父类能够调用子类的变量*/
sealed class NewsState {
    class Success(val Response:NewsModel):NewsState()
    class getConPersonData(val Data:ArrayList<Relationship>):NewsState()
    class Failure(val Error:Throwable):NewsState()
    object Loading:NewsState()
    object Empty:NewsState()

    /*定义是否需要进行socket连接*/
    class isExist(var vu:Boolean):NewsState()
}