package authorization.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User:RealmObject{
    @PrimaryKey
    var id:ObjectId = ObjectId()
    var name: String =""
    var password:String=""
}

