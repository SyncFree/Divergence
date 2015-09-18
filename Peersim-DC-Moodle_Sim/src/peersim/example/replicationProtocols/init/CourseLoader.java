package peersim.example.replicationProtocols.init;

import java.util.HashMap;
import java.util.Map;

import moodleLogic.Course;
import peersim.core.dcdatastore.initializers.databaseinit.DatabaseInitiator;
import peersim.core.dcdatastore.util.DataObject;

public class CourseLoader extends DatabaseInitiator {

	public CourseLoader(String prefix) {
		super(prefix);
	}

	@Override
	protected Map<String, DataObject<?, ?>> getDataToLoad() {
		String keys[] = {"COURSE_-1768490243","COURSE_-1914466859","COURSE_1435140924","COURSE_-1901537565"};
		
		Map<String, DataObject<?, ?>> objects = new HashMap<String, DataObject<?, ?>>();
		for (String key:keys){
			objects.put(key, new Course(key, 1000000));// 1M enrollmentLimit should be ok, no?
		}
		return objects;
	}

}
