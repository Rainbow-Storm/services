import com.rainbow.services.Course
import com.rainbow.services.User
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo

class UserRepository(private val mongo: ReactiveFluentMongoOperations) {

    fun findAll() = mongo.query<User>().flow()

    suspend fun findOne(id: String) = mongo.query<User>().matching(query(where("id").isEqualTo(id))).awaitOne()

    suspend fun insert(user: User) = mongo.insert<User>().oneAndAwait(user)

    suspend fun update(user: User) = mongo.update<User>().replaceWith(user).asType<User>().findReplaceAndAwait()!!

}

class CourseRepository(private val mongo: ReactiveFluentMongoOperations) {

    suspend fun findOne(id: String) = mongo.query<Course>().matching(query(where("id").isEqualTo(id))).awaitOne()

    suspend fun insert(course: Course) = mongo.insert<Course>().oneAndAwait(course)

    suspend fun findAll() = mongo.query<Course>().flow()

}