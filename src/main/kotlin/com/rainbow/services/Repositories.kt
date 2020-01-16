import com.rainbow.services.Course
import com.rainbow.services.User
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo

class UserRepository(private val mongo: ReactiveFluentMongoOperations) {

    fun queryAll() = mongo.query<User>().flow()

    suspend fun queryById(id: String) = mongo.query<User>().matching(query(where("id").isEqualTo(id))).awaitOne()

    suspend fun insert(user: User) = mongo.insert<User>().oneAndAwait(user)


}

class CourseRepository(private val mongo: ReactiveFluentMongoOperations) {

    suspend fun queryById(id: String) = mongo.query<Course>().matching(query(where("id").isEqualTo(id))).awaitOne()

    suspend fun insert(course: Course) = mongo.insert<Course>().oneAndAwait(course)
    suspend fun update(course: Course) = mongo.update<Course>().replaceWith(course).asType<Course>().findReplaceAndAwait()

    suspend fun queryAll() = mongo.query<Course>().flow()

}