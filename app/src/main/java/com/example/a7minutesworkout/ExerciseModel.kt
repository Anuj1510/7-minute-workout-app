package com.example.a7minutesworkout

/*
Your model would usually be a set of classes that hold your data and business logic.
 In this example case, probably an Item class having a name, name of painter and thumbnail properties.

In model class, there are mainly three methods ---

Constructor: It is a bridge between model and adapter class.

Getter method: This method is used for getting values of any property.

Setter method: This method is used for setting value of any property

So when user ask for an information though View so view goes to controller and controller notify the model.
 Then model give that information to controller and controller notify View about that information so the
 user can see that information.

 */

class ExerciseModel (
    private var id: Int,
    private var name : String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
        ){
    fun getId():Int{
        return id
    }

    fun setId(id: Int){
        this.id = id
    }

    fun getName() : String{
        return name
    }

    fun setName(name: String){
        this.name = name
    }

    fun getImage() : Int{
        return image
    }

    fun setImage(image: Int){
        this.image = image
    }

    fun getisCompleted() : Boolean{
        return isCompleted
    }

    fun setisCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }

    fun getisSelected() : Boolean{
        return isSelected
    }

    fun setisSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }

}