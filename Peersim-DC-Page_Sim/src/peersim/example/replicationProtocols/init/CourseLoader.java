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
		String key = "COURSE_-1768490243";
		
		Map<String, DataObject<?, ?>> objects = new HashMap<String, DataObject<?, ?>>();
		
		objects.put(key, new Course(key, 30));
		
		return objects;
	}

}
