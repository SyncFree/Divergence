package moodleLogic;


import static java.lang.Boolean.*;

import java.util.HashSet;
import java.util.Set;

import peersim.core.dcdatastore.util.DataObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class Course implements DataObject<Course, Integer>{
    private String id;
    private int enrollmentLimit;
    private int value;
    private Set<Calendar> Calendars;
    private Set<Assignment> Assignments;
    private Set<Module> Modules;
    private Set<Member> Members;
    private Set<Forum> Forums;
    private Set<Blog> Blogs;
    private Set<Directory> Directories;
    private Set<Quiz> Quizes;
    private Set<QuizAttempt> QuizAttempts;
    private Set<Resource> Resources;
    private Set<Page> Pages;
    private Set<Url> Urls;
    
    public Course(String courseId, int enrollmentLimit){
        this.id = courseId;    
        this.enrollmentLimit = enrollmentLimit;
        this.value = 0;
        this.Calendars = new HashSet<Calendar>();
        this.Assignments = new HashSet<Assignment>();
        this.Modules = new HashSet<Module>();
        this.Members = new HashSet<Member>();
        this.Forums = new HashSet<Forum>();
        this.Blogs = new HashSet<Blog>();
        this.Directories = new HashSet<Directory>();
        this.Quizes = new HashSet<Quiz>();
        this.QuizAttempts = new HashSet<QuizAttempt>();
        this.Resources = new HashSet<Resource>();
        this.Pages = new HashSet<Page>();
        this.Urls = new HashSet<Url>();
    }
    
    public Object clone(){
    	Course nc = null;
		try {
			nc = (Course) super.clone();
	    	nc.id = this.id;    
	        nc.enrollmentLimit = this.enrollmentLimit;
	        nc.value = this.value;
	        nc.Calendars = new HashSet<Calendar>(this.Calendars);
	        nc.Assignments = new HashSet<Assignment>(this.Assignments);
	        nc.Modules = new HashSet<Module>(this.Modules);
	        nc.Members = new HashSet<Member>(this.Members);
	        nc.Forums = new HashSet<Forum>(this.Forums);
	        nc.Blogs = new HashSet<Blog>(this.Blogs);
	        nc.Directories = new HashSet<Directory>(this.Directories);
	        nc.Quizes = new HashSet<Quiz>(this.Quizes);
	        nc.Resources = new HashSet<Resource>(this.Resources);
	        nc.Pages = new HashSet<Page>(this.Pages);
	        nc.Urls = new HashSet<Url>(this.Urls);
    	
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return nc;
    }
    
    @Override
    public String toString (){
    	// Will return a string with all the values 
    	String str = getValue() + " " + this.Pages.size() + " " + this.Resources.size() + " " + this.Forums.size() + " " + this.Quizes.size() + " " + this.Directories.size() + " ";
    	return str;
    }
    
    
    public String getId () {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public void incValue() {
        this.value+=1;
    }
    
    public Boolean existMember(String userId) {
        for (Member m:Members) {
            if (m.getId() == userId) return TRUE;
        }
        
        return FALSE;
    }
    public Member getMember(String userId) {
        for (Member m:Members) {
            if (m.getId() == userId) return m;
        }
        return null;
    }
    public void addMember(String userId){
        Members.add(new Member(userId, "student"));
        this.decEnrollmentLimit();
    }
    public void addMember(String userId, String role){
        Members.add(new Member(userId, role));
        this.decEnrollmentLimit();
    }
    public void deleteMember(String userId){
        Members.remove(getMember(userId));
        this.incEnrollmentLimit();
    }
   
    public void addCalendar(String calId) {
        Calendars.add(new Calendar(calId));
    }
    public void editCalendar(String calId) {
       this.getCalendar(calId).incValue(); 
    }
    public void deleteCalendar(String calId) {
        
        Calendars.remove(getCalendar(calId)); // Will he get upset because you shouldn't remove null objects???
    }
    public boolean existCalendar(String calId) {
        for (Calendar cal: Calendars) {
            if (cal.getId() == calId){
                return TRUE;
            }
        }
        return FALSE;
    }
    public Calendar getCalendar(String calId) {
        for (Calendar cal: Calendars) {
            if (cal.getId() == calId){
                return cal;
            }
        }
        return null;
    }

    public void addBlog(String blogId) {
        Blogs.add(new Blog(blogId));
    }
    public boolean existBlog(String blogId) {
        for (Blog b: Blogs) {
            if (b.getId() == blogId) return TRUE;
        }
            
        return FALSE;
    }
    public void deleteBlog(String blogId) {
        Blogs.remove(getBlog(blogId));
    }
    public Blog getBlog(String blogId) {
        for (Blog b: Blogs) {
            if (b.getId() == blogId){
                return b;
            }
        }
        return null;
    }
    public void editBlog(String blogId) {
        this.getBlog(blogId).incValue();
    }

    public boolean existDir(String dirId) {
        for (Directory d: Directories) {
            if (d.getId() == dirId) return TRUE;
        }
            
        return FALSE;    
    }
    public void addDir(String dirId) {
        Directories.add(new Directory(dirId));
    }
    public void deleteDir(String dirId) {
        Directories.remove(getDir(dirId));
    }
    public Directory getDir(String dirId) {
        for (Directory d: Directories) {
            if (d.getId() == dirId){
                return d;
            }
        }
        return null;
    }
    public void editDir(String dirId) {
        this.getDir(dirId).incValue();
    }
    
    public void addModule(String modId) {
        Modules.add(new Module(modId));
    }
    public boolean existModule(String modId) {
        for (Module m: Modules) {
            if (m.getId() == modId) return TRUE;
        }
            
        return FALSE;            
    }
    public void editModule(String modId) {
        this.getModule(modId).incValue();
    }
    
    public Module getModule(String modId) {
        for (Module m: Modules) {
            if (m.getId() == modId){
                return m;
            }
        }
        return null;
    }
    public void deleteModule(String modId){  
        // With which policy do we want to play?? 
        //      - Delete on cascade
        //      - Move section to orphanage
        //      - Cannot delete module
        //      - 
       Module m = this.getModule(modId);
    
       // We remove all the attached Sections
       m.Sections.clear();
       
       // We remove the module
       Modules.remove(m);
    } 
    
    public boolean existUrl(String urlId) {
        for (Url u: Urls) {
            if (u.getId() == urlId) return TRUE;
        }
        return FALSE;
    }
    
    public Url getUrl(String urlId) {
        for (Url u: Urls) {
            if (u.getId() == urlId) return u;
        }
        return null;
    }

    void addUrl(String urlId) {
        Urls.add(new Url(urlId));
    }

    void deleteUrl(String urlId) {
        Urls.remove(getUrl(urlId));
    }

    void viewUrl(String urlId) {
        // Do some viewing stuff
    }
    


    public int getEnrollmentLimit() {
        return this.enrollmentLimit;
    }
    private void incEnrollmentLimit() {
        this.enrollmentLimit+=1;
    }
    private void decEnrollmentLimit() {
        this.enrollmentLimit-=1;
    }

    public int countMemberType(String role) {
        int count = 0;
        for (Member m: Members) {
            if (m.getRole().equals(role)) count+=1;
        }
        return count;
    }

    public boolean existForum(String forId) {
        for (Forum f: Forums) {
            if (f.getId() == forId) {
                return TRUE;
            }
        }
        return FALSE;
    }
    public Forum getForum(String forId) {
        for (Forum f: Forums) {
            if (f.getId() == forId) {
                return f;
            }
        }
        return null;
    }
    void addForum(String forId) {
        Forums.add(new Forum(forId));
    }

    public void addQuiz(String quizId, int maxAttemps) {
        Quizes.add(new Quiz(quizId, maxAttemps, Members));
    }
    
    public Quiz getQuiz(String quizId) {
        for (Quiz q: Quizes) {
            if (q.getId() == quizId) return q;
        }
        return null;
    }    
    public Boolean existQuiz(String quizId) {
        for (Quiz q: Quizes) {
            if (q.getId() == quizId) return TRUE;
        }
        return FALSE;
    }

    public void addResource(String resId) {
        Resources.add(new Resource(resId));
    }
    public boolean existResource(String resId) {
        for (Resource r: Resources) {
            if (r.getId() == resId) return TRUE;
        }
            
        return FALSE;
    }
    public void deleteResource(String resId) {
        Resources.remove(getResource(resId));
    }
    public Resource getResource(String resId) {
        for (Resource r: Resources) {
            if (r.getId().equals(resId)){
                return r;
            }
        }
        return null;
    }
    public void editResource(String resId) {
        this.getResource(resId).incValue();
    }

    public boolean existAssignment(String assId) {
        for (Assignment a: Assignments){
            if (a.getId() == assId) return TRUE;
        }
        return FALSE;
    }
    public Assignment getAssignment(String assId) {
        for (Assignment a: Assignments){
            if (a.getId() == assId) return a;
        }
        return null;
    }
    public void addAssignment(String assId) {
        Assignments.add(new Assignment(assId));
    }
    public void deleteAssignment(String assId) {
        Assignments.remove(getAssignment(assId));
    }

    Set<Assignment> getAssignments() {
        return Assignments;
    }

    protected void viewCalendar(String calId) {
        //DO some Viewing stuff
    }

    protected void viewBlog(String blogId) {
        //DO some Viewing stuff
    }

    protected void viewDir(String dirId) {
        // Do some viewing stuff
    }

    protected void viewCourse() {
        // Do some viewing stuff
    }

    protected void viewCourseReport() {
        // Do some viewing stuff    
    }

    protected void viewCourseReportOutline() {
        // Do some viewing stuff
    }

    protected void viewForumAll() {
        // Do some viewing stuff
    }

    protected void viewForumSearch() {
        // Do some viewing stuff
    }

    protected void viewForumDiscussion() {
        // Do some viewing stuff
    }

    protected void viewForum(String forId) {
        // Do some viewing stuff
    }

    protected void viewQuizAll() {
        // Do some viewing stuff
    }

    protected void viewQuiz() {
        // Do some viewing stuff
    }

    protected void viewQuizPreview() {
        // Do some viewing stuff
    }

    protected void viewQuizSummary() {
        // Do some viewing stuff
    }

    protected void viewQuizReport() {
        // Do some viewing stuff
    }

    protected void viewPage(String pageId) {
        // Do some viewing Stuff
    }
    
    protected void viewModule(String modId) {
        // Do viewy stuff
    }
    
    protected void viewResource(String resId) {
        //Do sth viewy.
    }
    protected void viewAllResource(){
        //Do sth viewy.
    }

    protected void addPage(String pageId) {
        Pages.add(new Page(pageId));
    }
    protected void editPage(String pageId) {
       this.getPage(pageId).incValue(); 
    }
    protected void deletePage(String pageId) {
        
        Pages.remove(getPage(pageId)); 
    }
    public boolean existPage(String pageId) {
        for (Page p: Pages) {
            if (p.getId().equals(pageId)){
                return TRUE;
            }
        }
        return FALSE;
    }
    public Page getPage(String pageId) {
        for (Page p: Pages) {
            if (p.getId().equals(pageId)){
                return p;
            }
        }
        return null;
    }
    
    
    public int CalendarAddOperation(String userId, String calId ) {
        
            // add the new calendar
            if (existCalendar(calId)) {
                return 0;
            } else {
                addCalendar(calId);
            }
        return 1;
    }
    public int CalendarEditOperation(String userId, String calId ) {
        if(existCalendar(calId)) {
            editCalendar(calId);
        } else {
            addCalendar(calId);
        }
        
        return 1;
    }
    public int CalendarDeleteOperation(String userId, String calId ) {
    	if(existCalendar(calId)) {
            editCalendar(calId);
        } else {
            addCalendar(calId);
        }
        
        return 1;  
    }
    public int CalendarViewOperation(String userId, String calId) {
            if (existCalendar(calId)) {
                viewCalendar(calId);
            } else {
                addCalendar(calId);
            }
        return 1;
    }
    
    public int BlogAddOperation(String userId,  String blogId){
            if (existBlog(blogId)) {
                return 0;
            }
            else {
                addBlog(blogId);
            }
        
        return 1;
    }
    public int BlogViewOperation(String userId,  String blogId){
            if (!existBlog(blogId)) {
                addBlog(blogId);
            }
            else {
                viewBlog(blogId);
            }
        
        return 1;
    }
    public int BlogDeleteOperation(String userId,  String blogId){
            if (!existBlog(blogId)) {
                return 0;
            }
            else {
                deleteBlog(blogId);
            }
        
        return 1;
    }
    public int BlogEditOperation(String userId, String blogId) {
            if (!existBlog(blogId)) {
                addBlog(blogId);
            }
            else {
                editBlog(blogId);
            }
        return 1;    
    }

    
    public int DirAddOperation(String userId, String dirId){
            if (existDir(dirId)) {
                return 0;
            }
            else {
                addDir(dirId);
            }
        
        return 1;
    }
    public int DirViewOperation( String userId, String dirId){
            if (existDir(dirId)) {
                viewDir(dirId);
            }
            else {
                return 0;
            }
        
        return 1;
    }
    public int DirDeleteOperation(String userId, String dirId){
            if (!existDir(dirId)) {
                return 0;
            }
            else {
                deleteDir(dirId);
            }
        
        return 1;
    }
    public int DirEditOperation(String userId, String dirId){
            if (!existDir(dirId)) {
                addDir(dirId);
            }
            else {
                editDir(dirId);
            }
        
        return 1;
    }
    
    public int ForumAddOperation(String userId, String forId) {
    	forId = "default"; 	// This should the sent to the operation and not changed here...	
    						// But then we have to update the other ForumOp to have the forId set to "default"
    	if (existForum(forId)) {
    		return 0;
    	} else {
    		addForum(forId);
    	}
        return 1;
    }
    public int ForumUpdateOperation(String userId, String forId) {
        forId = "default";
        
    	if (!existForum(forId)) {
    		addForum(forId);
    	} else {
    		getForum(forId).incValue();
    	}
        return 1;
    }
    public int ForumAddDiscussionOperation(String userId, String disId) {
    	String forId = "default";
    				
    	if (!existForum(forId)) {
    		addForum(forId);
    	}
    	if (getForum(forId).existDiscussion(disId) ) {
    		return 0;
    	} else {
    		getForum(forId).addDiscussion(disId);
    	}
    	
        return 1;
    }
    public int ForumUpdateDiscussionOperation(String userId, String disId) {
    	String forId = "default";
    				if (!existForum(forId)) {
                        return 0;
                    } else {
                        if (!getForum(forId).existDiscussion(disId) ) {
                            return 0;
                        } else {
                            getForum(forId).getDiscussion(disId).incValue();
                        }
                    }
        return 1;
    }
    public int ForumAddPostOperation(String userId, String disId, String posId) {
    	String forId = "default";
    	if (!existForum(forId)) {
                    return 0;
                } else {
                    if (!getForum(forId).existDiscussion(disId) ) {
                        return 0;
                    } else {
                        if(getForum(forId).getDiscussion(disId).existPost(posId)) {
                            return 0;
                        } else {
                            getForum(forId).getDiscussion(disId).addPost(posId, userId);
                        }
                    }
                }
        return 1;
    }
    public int ForumUpdatePostOperation(String userId, String disId, String posId) {
    	String forId = "default";
    	if (!existForum(forId)) {
                    return 0;
                } else {
                    if (!getForum(forId).existDiscussion(disId) ) {
                        return 0;
                    } else {
                        if(!getForum(forId).getDiscussion(disId).existPost(posId)) {
                            return 0;
                        } else {
                        	getForum(forId).getDiscussion(disId).getPost(posId).incValue();
                        }
                    }
                }
        return 1;
    }
    public int ForumUserReportOperation(String userId, String disId, String posId) {
        // Any user can report a post.
    	String forId = "default";
            	if (!existForum(forId)) {
                    return 0;
                } else {
                    if (!getForum(forId).existDiscussion(disId) ) {
                        return 0;
                    } else {
                        if(!getForum(forId).getDiscussion(disId).existPost(posId)) {
                            return 0;
                        } else {
                            getForum(forId).getDiscussion(disId).getPost(posId).setReported(TRUE);
                        }
                    }
                }
            	
        return 1;
    }
    public int ForumSubscribeOperation( String userId, String forId) {
    	forId = "default"; 	
    			if (!existForum(forId)) {
                    return 0;
                } else {
                    if (getForum(forId).existSubscriber(userId)){
                        return 0;
                    } else {
                        getForum(forId).addSubscriber(userId);
                    }
                }
        return 1;
    }
    public int ForumUnsubscribeOperation(String userId,String forId) {
    	forId = "default";
    			if (!existForum(forId)) {
                    return 0;
                } else {
                    if (!getForum(forId).existSubscriber(userId)){
                        return 0;
                    } else {
                        getForum(forId).deleteSubscriber(userId);
                    }
                }
        return 1;
    }
    public int ForumSearchOperation(String userId){
    	viewForumSearch();
        return 1;
    }
    public int ForumViewOperation(String userId, String forId){
    	forId = "default";
    		if (existForum(forId)){
                viewForum(forId);
            } else {
                return 0;
            }
        return 1;
    }
    public int ForumViewAllOperation(String userId){
    	viewForumAll();
        return 1;
    }
    public int ForumViewDiscussionOperation(String userId, String disId){
    	String forId = "default";
        if (existForum(forId)){
            if (getForum(forId).existDiscussion(disId)){
                viewForumDiscussion();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
        return 1;
    }

    public int ResourceAddOperation(String userId, String resId){
            if (existResource(resId)) {
                return 0;
            }
            else {
                addResource(resId);
            }
        return 1;
    }
    public int ResourceViewOperation(String userId, String resId){
            if (!existResource(resId)) {
                return 0;
            }
            else {
                viewResource(resId);
            }        
        return 1;
    }
    public int ResourceViewAllOperation(String userId){
        viewAllResource();
        return 1;
    }
    public int ResourceDeleteOperation(String userId, String resId){
            if (!existResource(resId)) {
                return 0;
            }
            else {
                deleteResource(resId);
            }

        return 1;
    }
    public int ResourceEditOperation(String userId, String resId){
            if (!existResource(resId)) {
                return 0;
            }
            else {
                editResource(resId);
            }
        
        return 1;
    }

    public int UrlAddOperation(String userId, String urlId) {
            if (existUrl(urlId)){
            	return 0;
            } else {
            	addUrl(urlId);
            }
        
        return 1;
    }
    public int UrlEditOperation(String userId, String urlId) {
                    if (!existUrl(urlId)){
                        addUrl(urlId);
                    } else {
                        getUrl(urlId).incValue();
                    }

        
        return 1;
    }    
    public int UrlDeleteOperation(String userId, String urlId) {
            if (existUrl(urlId)){
            		deleteUrl(urlId);
            } else{
                return 0;
            }
        
        return 1;
    }
    public int UrlViewOperation(String userId, String urlId) {
        if (!existUrl(urlId)){
        	return 0;
        } else {
        	viewUrl(urlId);
        }
        
        return 1;
    }

    public int PageAddOperation(String userId, String pageId){
                if (!existPage(pageId)){
                    addPage(pageId);
                } else {
                    return 0;
                }
        return 1;
    }
    public int PageViewOperation(String userId, String pageId){
                if (existPage(pageId)){
                    viewPage(pageId);
                } else {
                    return 0;
                }
        return 1;    
    
    }   
    public int PageEditOperation(String userId, String pageId){
                if (existPage(pageId)){
                    getPage(pageId).incValue();
                } else {
                    return 0;
                }
        return 1;
    }
    public int PageDeleteOperation(String userId, String pageId){
                if (existPage(pageId)){
                    deletePage(pageId);
                } else {
                    return 0;
                }
        return 1;
    }
    
    public int CourseAddModuleOperation(String userId, String modId){
            if (existModule(modId)) {
                return 0;
            }
            else {
                addModule(modId);
            }        
        return 1;
    }
    public int CourseEditModuleOperation(String userId, String modId){
            if (!existModule(modId)) {
                return 0;
            }
            else {
                editModule(modId);
            }

        
        return 1;
    }
    public int CourseDeleteModuleOperation(String userId, String modId){
            if (!existModule(modId)) {
                return 0;
            }
            else {
                deleteModule(modId);
            }

        
        return 1;
    }
    public int CourseViewModuleOperation(String userId, String modId){
            if (!existModule(modId)) {
                return 0;
            }
            else {
                viewModule(modId);
            }
        
        return 1;
    }
    
    public int CourseAddSectionOperation (String userId, String modId, String secId) {
        
            if (!existModule(modId)) {
                return 0;
            }
            else {
                Module m = getModule(modId);
                if (!m.existSection(secId)) {
                    m.addSection(secId);
                } else {
                    return 0;
                }
            }
        return 1;
    
    }
    public int CourseEditSectionOperation (String userId, String modId, String secId) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                Module m = getModule(modId);
                if (m.existSection(secId)) {
                    m.getSection(secId).incValue();
                } else {
                    return 0;
                }
            }        
        return 1;
    
    }
    public int CourseDeleteSectionOperation (String userId, String modId, String secId) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                Module m = getModule(modId);
                if (m.existSection(secId)) {
                    m.deleteSection(secId);
                } else {
                    return 0;
                }
            }
        
        return 1;
    
    }
    public int CourseViewSectionOperation (String userId, String modId, String secId) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                Module m = getModule(modId);
                if (m.existSection(secId)) {
                    m.viewSection(secId);
                } else {
                    return 0;
                }
            }

        return 1;
    
    }
    
    public int CourseEnrollOperation (Users us, String userId) {
        // Check that userId exists in Users
        // Check that courseId exists in Courses
        // Check that userId is not enrolled in courseId
        // Check that there are still places in the course.
//        if (!us.existUser(userId)){
//            return 0;
//        } else {
            if (existMember(userId)) {
                    return 0;
                } else {
                    if (getEnrollmentLimit() > 0 ) {
                    // enroll him!
                        addMember(userId, "student");
                    } else {
                        return 0;
                    }
                }
//            }
        
        return 1;
    }
    public int CourseEnrollGuestOperation (Users us, String userId) {
        // Check that userId exists in Users
        // Check that courseId exists in Courses
        // Check that userId is not enrolled in courseId
        // Check that there are still places in the course.
//        if (!us.existUser(userId)){
//            return 0;
//        } else {
//            if (existMember(userId)) {
//                    return 0;
//                } else {
                    if (getEnrollmentLimit() > 0 ) {
                    // enroll him!
                        addMember(userId, "guest");
                    } else {
                        return 0;
                    }
//                }
//            }
        
        return 1;
    }
    public int CourseUnenrollOperation (String userId) {
        // Check that userId exists in Users
        // Check that courseId exists in Courses
        // Check that userId is enrolled in courseId
        // Check that there is at least one staff left
        // Check that there are still places in the course.
//            if (!existMember(userId)) {
//                    return 0;
//                } else {
                    if (getMember(userId).getRole().equals("staff") ) {
                        if (countMemberType("staff") > 1)
                            deleteMember(userId);
                        else {
                            return 0;
                        }
                    } else {
                        deleteMember(userId);
                    }
//                }
        
        return 1;
    }
    public int CourseEditOperation () {
    	incValue();
        return 1;
    }
    public int CourseViewOperation() {
        viewCourse();
        return 1;
    }
    public int CourseReportLogOperation() {
            viewCourseReport();
        return 1;
    }
    public int CourseReportOutlineOperation() {
    	viewCourseReportOutline();
        return 1;
    }
    public int CourseRoleAssignOperation (String userId, String objId) {
    	if (existMember(userId)){
    		getMember(userId).incValue();
    	} else{
    		addMember(userId);
    	}
    		
        return 1;
    }
    public int CourseRoleUnassignOperation (String userId, String objId) {
    	if (existMember(userId)){
    		getMember(userId).incValue();
    	} else{
    		addMember(userId);
    	}
        return 1;
    }

    
    public int QuizAddOperation(String userId, String quizId, int maxAttemps){
    
            if (existQuiz(quizId)) {
                return 0;
            } else {
                addQuiz(quizId, maxAttemps);
            }

        return 1;
    }
    public int QuizAttemptOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        if (existQuizAttempt(quizId)){
        	return 0;
        } else{
        	addQuizAttempt(quizId);
        }
    	
        return 1;
    }
    public int QuizContinueAttemptOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        if (existQuizAttempt(quizId)){
        	getQuizAttempt(quizId).incValue();
        } else{
        	addQuizAttempt(quizId);
        }
    	
        return 1;
    }
    private void addQuizAttempt(String quizId) {
    	QuizAttempts.add(new QuizAttempt(quizId));
	}

	private boolean existQuizAttempt(String quizId) {
		for (QuizAttempt qa: QuizAttempts){
			if (qa.getId() == quizId) return true;
		}
		return false;
	}
	
	private QuizAttempt getQuizAttempt(String quizId) {
		for (QuizAttempt qa: QuizAttempts){
			if (qa.getId() == quizId) return qa;
		}
		return null;
	}

	public int QuizCloseAttemptOperation(String userId, String quizId) {
		if (existQuizAttempt(quizId)){
			getQuizAttempt(quizId).incValue();
		} else {
			addQuizAttempt(quizId);
		}
		
        return 1;
    }
//    public int QuizContinueAttemptOperation(String userId, String quizId) {
//        // User has to be a student to attempt the quiz.
//        if (!existMember(userId)) {
//                return 0;
//            } else {
//                    if (existQuiz(quizId)) {
//                        if (!getQuiz(quizId).getQuizStudent(userId).getOpen()) {
//                            return 0;
//                        } else {
//                            if (!getQuiz(quizId).getQuizStudent(userId).getStopped()) {
//                                return 0;
//                            } else {
//                                getQuiz(quizId).getQuizStudent(userId).setOpen(TRUE);
//                                getQuiz(quizId).getQuizStudent(userId).setStopped(FALSE);
//                            }
//                        }
//   
//                } else {
//                    return 0;
//                }
//            }
//        return 1;
//    }
    public int QuizStopAttemptOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        if (!existMember(userId)) {
                return 0;
            } else {
                    if (existQuiz(quizId)) {
                        if (!getQuiz(quizId).getQuizStudent(userId).getOpen()) {
                            return 0;
                        } else {
                            if (getQuiz(quizId).getQuizStudent(userId).getStopped()) {
                                // If it has been stopped you have to continue it, no attemp again.
                                return 0;
                            } else {
                                getQuiz(quizId).getQuizStudent(userId).setOpen(TRUE);
                                getQuiz(quizId).getQuizStudent(userId).setStopped(TRUE);
                            }
                        }
                    } else {
                        return 0;
                    }    
            }
        return 1;
    }
    public int QuizEditOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.

    	if (existQuiz(quizId)) {
        	getQuiz(quizId).incValue();
    	} else {
        	addQuiz(quizId, 100); // 100 = maxAttempts of the Quiz.
        }    
        return 1;
    }
    public int QuizManualGradeOperation(String userId, String quizId, String studentId, int grade) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                    if (existQuiz(quizId)) {
                        // Should it be open or closed???
                        // Should we close the exam??
                        getQuiz(quizId).getQuizStudent(studentId).setGrade(grade);
                    } else {
                        return 0;
                    }    
            }
        return 1;
    }
    public int QuizPreviewOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                    if (existQuiz(quizId)) {
                       viewQuizPreview();
                    } else {
                        return 0;
                    }    
            }
        return 1;
    }
    public int QuizReportOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
                if (existQuiz(quizId)) {
                   viewQuizReport();
                } else {
                    return 0;
                }
        return 1;
    }
    public int QuizViewOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.

                if (existQuiz(quizId)) {
                    viewQuiz();
                } else {
                    return 0;
                }

        return 1;
    }
    public int QuizViewAllOperation(String userId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
    	viewQuizAll();

        return 1;
    }
    public int QuizViewSummaryOperation(String userId, String quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
                    if (existQuiz(quizId)) {
                       viewQuizSummary();
                    } else {
                        return 0;
                    }
        return 1;
    }
    
    public int AssignmentAddOperation(String userId, String assId) {
                if (!existAssignment(assId)) {
                    addAssignment(assId);
                } else {
                    return 0;
                }
        return 1;
    }
    public int AssignmentUpdateOperation(String userId, String assId) {
//                if (existAssignment(assId)) {
//                        getAssignment(assId).incValue();
//                } else {
//                    return 0;
//                }
//     
        return 1;
    }
    public int AssignmentUpdateSubmissionOperation(String userId, String assId) {
//        if (existMember(userId) && getMember(userId).getRole().equals("student")) {
//                if (existAssignment(assId)) {
//                    if (getAssignment(assId).existSubmission(userId)){
//                        getAssignment(assId).getSubmission(userId).incValue();
//                    } else {
//                        return 0;
//                    }
//                } else {
//                    return 0;
//                }
//            } else {
//                return 0;
//            }
        return 1;
    }
    public int AssignmentUpdateGradesOperation(String userId, String assId) {
                if (existAssignment(assId)) {
                	getAssignment(assId).incValue();
    			} else {
                	addAssignment(assId);
    			}
        return 1;
    }
    public int AssignmentUploadOperation(String userId, String assId) {
                if (existAssignment(assId)) {
                    getAssignment(assId).addSubmission(userId);
                } else {
                    addAssignment(assId);
                }
        return 1;
    }
    public int AssignmentViewSubmissionOperation(String userId, String assId) {
//            if (existMember(userId) && getMember(userId).getRole().equals("student")) {
//                if (existAssignment(assId)) {
//                    if (getAssignment(assId).existSubmission(userId)) {
//                        getAssignment(assId).viewSubmission(userId);
//                    }else {
//                        return 0;
//                    }
//                } else {
//                    return 0;
//                }
//            } else {
//                return 0;
//            }
        return 1;
    }
    public int AssignmentViewAllOperation(String userId) {
                for (Assignment a: getAssignments()){
                    // We view all the submissions.
                    a.viewSubmission(userId);
                }
        return 1;
    }
    public int AssignmentViewOperation(String userId, String assId) {
                if (existAssignment(assId)) {
                    getAssignment(assId).viewAssignment();
                } else {
                	addAssignment(assId);
                }
        return 1;
    }
	
    @Override
	public Course getData() {
		return this;
	}
	@Override
	public void setData(Course data) {
	    this.id = data.id;
	    this.enrollmentLimit = data.enrollmentLimit;
	    this.value = data.value;
	    this.Calendars = data.Calendars;
	    this.Assignments = data.Assignments;
	    this.Modules = data.Modules;
	    this.Members = data.Members;
	    this.Forums = data.Forums;
	    this.Blogs = data.Blogs;
	    this.Directories = data.Directories;
	    this.Quizes = data.Quizes;
	    this.QuizAttempts = data.QuizAttempts;
	    this.Resources = data.Resources;
	    this.Pages = data.Pages;
	    this.Urls = data.Urls;
	}
	@Override
	public void setData(Course data, Integer metadata) {
		this.id = data.id;
	    this.enrollmentLimit = data.enrollmentLimit;
	    this.value = data.value;					// value is changed twice!!!!!
	    this.Calendars = data.Calendars;
	    this.Assignments = data.Assignments;
	    this.Modules = data.Modules;
	    this.Members = data.Members;
	    this.Forums = data.Forums;
	    this.Blogs = data.Blogs;
	    this.Directories = data.Directories;
	    this.Quizes = data.Quizes;
	    this.QuizAttempts = data.QuizAttempts;
	    this.Resources = data.Resources;
	    this.Pages = data.Pages;
	    this.Urls = data.Urls;
	    this.value = metadata;
	}
	@Override
	public Integer getMetadata() {
		return value;
	}
	@Override
	public double computeDivergence(DataObject<?, ?> other) {
		int val = 0;
		int valOther = 0;
		
		if (other instanceof Course){
		
			//return Math.abs(this.value - (Integer) other.getMetadata());
			Course cother = (Course) other;

			// This versions value
			for (Page p: Pages) val += p.getValue() + 1; 
			for (Forum f: Forums) val += f.getValue() + 1;
		    for ( Assignment o: Assignments) val += o.getValue() + 1; 
		    for ( Module o: Modules) val += o.getValue() + 1;
		    val += Members.size();
		    for ( Member o: Members) valOther += o.getValue() + 1;
		    for ( Blog o: Blogs) val += o.getValue() + 1;
		    for ( Directory o: Directories) val += o.getValue() + 1;
		    for ( Quiz o: Quizes) val += o.getValue() + 1;
		    for ( QuizAttempt o: QuizAttempts) val += o.getValue() + 1;
		    for ( Resource o: Resources) val += o.getValue() + 1;
		    val += value;
		    
			// Computing the OTTER value
		    for (Page p: cother.Pages) valOther += p.getValue() + 1; 
			for (Forum f: cother.Forums) valOther += f.getValue() + 1;
		    for ( Assignment o: cother.Assignments) valOther += o.getValue() + 1; 
		    for ( Module o: cother.Modules) valOther += o.getValue() + 1;
		    valOther += cother.Members.size();
		    for ( Member o: cother.Members) valOther += o.getValue() + 1;
		    for ( Blog o: cother.Blogs) valOther += o.getValue() + 1;
		    for ( Directory o: cother.Directories) valOther += o.getValue() + 1;
		    for ( Quiz o: cother.Quizes) valOther += o.getValue() + 1;
		    for ( QuizAttempt o: cother.QuizAttempts) valOther += o.getValue() + 1;
		    for ( Resource o: cother.Resources) valOther += o.getValue() + 1;
		    valOther += cother.value;
			
		}
		System.err.println("" + val+ "  " + valOther);
		return Math.abs(val - valOther);
	}

	public void GenericView(String objId) {
		// Generic Empty View
		// this way it will return the course anyway and the divergence can b computed in the client.
	}
    
    
}



