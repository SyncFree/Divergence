package moodleLogic;


import static java.lang.Boolean.*;
import java.util.HashSet;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class Course {
    private int id;
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
    private Set<Resource> Resources;
    private Set<Page> Pages;
    
    Course(int courseId, int enrollmentLimit){
        this.id = courseId;    
        this.enrollmentLimit = enrollmentLimit;
        this.value = 0;
        this.Calendars = new HashSet();
        this.Assignments = new HashSet();
        this.Modules = new HashSet();
        this.Members = new HashSet();
        this.Forums = new HashSet();
        this.Blogs = new HashSet();
        this.Directories = new HashSet();
        this.Quizes = new HashSet();
        this.Resources = new HashSet();
        this.Pages = new HashSet();
    }
    
    
    public int getId () {
        return this.id;
    }
    public void setId(int id) {
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
    
    public Boolean existMember(int userId) {
        for (Member m:Members) {
            if (m.getId() == userId) return TRUE;
        }
        
        return FALSE;
    }
    public Member getMember(int userId) {
        for (Member m:Members) {
            if (m.getId() == userId) return m;
        }
        return null;
    }
    public void addMember(int userId, String role){
        Members.add(new Member(userId, role));
        this.decEnrollmentLimit();
    }
    public void deleteMember(int userId){
        Members.remove(getMember(userId));
        this.incEnrollmentLimit();
    }
   
    public void addCalendar(int calId) {
        Calendars.add(new Calendar(calId));
    }
    public void editCalendar(int calId) {
       this.getCalendar(calId).incValue(); 
    }
    public void deleteCalendar(int calId) {
        
        Calendars.remove(getCalendar(calId)); // Will he get upset because you shouldn't remove null objects???
    }
    public boolean existCalendar(int calId) {
        for (Calendar cal: Calendars) {
            if (cal.getId() == calId){
                return TRUE;
            }
        }
        return FALSE;
    }
    public Calendar getCalendar(int calId) {
        for (Calendar cal: Calendars) {
            if (cal.getId() == calId){
                return cal;
            }
        }
        return null;
    }

    public void addBlog(int blogId) {
        Blogs.add(new Blog(blogId));
    }
    public boolean existBlog(int blogId) {
        for (Blog b: Blogs) {
            if (b.getId() == blogId) return TRUE;
        }
            
        return FALSE;
    }
    public void deleteBlog(int blogId) {
        Blogs.remove(getBlog(blogId));
    }
    public Blog getBlog(int blogId) {
        for (Blog b: Blogs) {
            if (b.getId() == blogId){
                return b;
            }
        }
        return null;
    }
    public void editBlog(int blogId) {
        this.getBlog(blogId).incValue();
    }

    public boolean existDir(int dirId) {
        for (Directory d: Directories) {
            if (d.getId() == dirId) return TRUE;
        }
            
        return FALSE;    
    }
    public void addDir(int dirId) {
        Directories.add(new Directory(dirId));
    }
    public void deleteDir(int dirId) {
        Directories.remove(getDir(dirId));
    }
    public Directory getDir(int dirId) {
        for (Directory d: Directories) {
            if (d.getId() == dirId){
                return d;
            }
        }
        return null;
    }
    public void editDir(int dirId) {
        this.getDir(dirId).incValue();
    }
    
    public void addModule(int modId) {
        Modules.add(new Module(modId));
    }
    public boolean existModule(int modId) {
        for (Module m: Modules) {
            if (m.getId() == modId) return TRUE;
        }
            
        return FALSE;            
    }
    public void editModule(int modId) {
        this.getModule(modId).incValue();
    }
    
    public Module getModule(int modId) {
        for (Module m: Modules) {
            if (m.getId() == modId){
                return m;
            }
        }
        return null;
    }
    public void deleteModule(int modId){  
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

    public boolean existForum(int forId) {
        for (Forum f: Forums) {
            if (f.getId() == forId) {
                return TRUE;
            }
        }
        return FALSE;
    }
    public Forum getForum(int forId) {
        for (Forum f: Forums) {
            if (f.getId() == forId) {
                return f;
            }
        }
        return null;
    }
    void addForum(int forId) {
        Forums.add(new Forum(forId));
    }

    public void addQuiz(int quizId, int maxAttemps) {
        Quizes.add(new Quiz(quizId, maxAttemps, Members));
    }
    
    public Quiz getQuiz(int quizId) {
        for (Quiz q: Quizes) {
            if (q.getId() == quizId) return q;
        }
        return null;
    }    
    public Boolean existQuiz(int quizId) {
        for (Quiz q: Quizes) {
            if (q.getId() == quizId) return TRUE;
        }
        return FALSE;
    }

    public void addResource(int resId) {
        Resources.add(new Resource(resId));
    }
    public boolean existResource(int resId) {
        for (Resource r: Resources) {
            if (r.getId() == resId) return TRUE;
        }
            
        return FALSE;
    }
    public void deleteResource(int resId) {
        Resources.remove(getResource(resId));
    }
    public Resource getResource(int resId) {
        for (Resource r: Resources) {
            if (r.getId() == resId){
                return r;
            }
        }
        return null;
    }
    public void editResource(int resId) {
        this.getResource(resId).incValue();
    }

    public boolean existAssignment(int assId) {
        for (Assignment a: Assignments){
            if (a.getId() == assId) return TRUE;
        }
        return FALSE;
    }
    public Assignment getAssignment(int assId) {
        for (Assignment a: Assignments){
            if (a.getId() == assId) return a;
        }
        return null;
    }
    public void addAssignment(int assId) {
        Assignments.add(new Assignment(assId));
    }
    public void deleteAssignment(int assId) {
        Assignments.remove(getAssignment(assId));
    }

    Set<Assignment> getAssignments() {
        return Assignments;
    }

    protected void viewCalendar(int calId) {
        //DO some Viewing stuff
    }

    protected void viewBlog(int blogId) {
        //DO some Viewing stuff
    }

    protected void viewDir(int dirId) {
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

    protected void viewForum(int forId) {
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

    protected void viewPage(int pageId) {
        // Do some viewing Stuff
    }
    
    protected void viewModule(int modId) {
        // Do viewy stuff
    }
    
    protected void viewResource(int resId) {
        //Do sth viewy.
    }
    protected void viewAllResource(){
        //Do sth viewy.
    }

    protected void addPage(int pageId) {
        Pages.add(new Page(pageId));
    }
    protected void editPage(int pageId) {
       this.getPage(pageId).incValue(); 
    }
    protected void deletePage(int pageId) {
        
        Pages.remove(getPage(pageId)); 
    }
    public boolean existPage(int pageId) {
        for (Page p: Pages) {
            if (p.getId() == pageId){
                return TRUE;
            }
        }
        return FALSE;
    }
    public Page getPage(int pageId) {
        for (Page p: Pages) {
            if (p.getId() == pageId){
                return p;
            }
        }
        return null;
    }
    
    
    public int CalendarAddOperation(int userId, int calId ) {
        
        if (existMember(userId) && getMember(userId).getRole().equals("staff") ){
            // add the new calendar
            if (existCalendar(calId)) {
                return 0;
            } else {
                addCalendar(calId);
            }
        } else {
            return 0;
        }
       
        return 1;
    }
    public int CalendarEditOperation(int userId, int calId ) {
        if(this.existMember(userId) && this.getMember(userId).getRole().equals("staff")) {
            this.editCalendar(calId);
        } else {
            return 0;
        }
        
        return 1;
    }
    public int CalendarDeleteOperation(int userId, int calId ) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            deleteCalendar(calId);
        } else {
            return 0;
        }
        
        return 1;  
    }
    public int CalendarViewOperation(int userId, int calId) {
        if (existMember(userId)){
            if (existCalendar(calId)) {
                viewCalendar(calId);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
       
        return 1;
    }
    
    public int BlogAddOperation(int userId,  int blogId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (existBlog(blogId)) {
                return 0;
            }
            else {
                addBlog(blogId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int BlogViewOperation(int userId,  int blogId){
        if(existMember(userId)) {
            if (!existBlog(blogId)) {
                return 0;
            }
            else {
                viewBlog(blogId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int BlogDeleteOperation(int userId,  int blogId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existBlog(blogId)) {
                return 0;
            }
            else {
                deleteBlog(blogId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int BlogEditOperation(int userId, int blogId) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existBlog(blogId)) {
                return 0;
            }
            else {
                editBlog(blogId);
            }
        } else {
            return 0;
        }
        
        return 1;    
    }

    
    public int DirAddOperation(int userId, int dirId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (existDir(dirId)) {
                return 0;
            }
            else {
                addDir(dirId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int DirViewOperation( int userId, int dirId){
        if(existMember(userId)) {
            if (existDir(dirId)) {
                viewDir(dirId);
            }
            else {
                return 0;
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int DirDeleteOperation(int userId, int dirId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existDir(dirId)) {
                return 0;
            }
            else {
                deleteDir(dirId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int DirEditOperation(int userId, int dirId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existDir(dirId)) {
                return 0;
            }
            else {
                editDir(dirId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    
    public int ForumAddOperation(int userId, int forId) {
                if (existMember(userId) && getMember(userId).getRole().equals("staff") ) {
                    if (existForum(forId)) {
                        return 0;
                    } else {
                        addForum(forId);
                    }
                } else {
                    return 0;
                }
        return 1;
    }
    public int ForumUpdateOperation(int userId, int forId) {
            if (!existMember(userId) ) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (!existForum(forId)) {
                        return 0;
                    } else {
                        getForum(forId).incValue();
                    }
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int ForumAddDiscussionOperation(int userId, int forId, int disId) {
            if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (!existForum(forId)) {
                        return 0;
                    } else {
                        if (getForum(forId).existDiscussion(disId) ) {
                            return 0;
                        } else {
                            getForum(forId).addDiscussion(disId);
                        }
                    }
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int ForumUpdateDiscussionOperation(int userId, int forId, int disId) {
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (!existForum(forId)) {
                        return 0;
                    } else {
                        if (!getForum(forId).existDiscussion(disId) ) {
                            return 0;
                        } else {
                            getForum(forId).getDiscussion(disId).incValue();
                        }
                    }
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int ForumAddPostOperation(int userId, int forId, int disId, int posId) {
            if (!existMember(userId)) {
                return 0;
            } else {
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
            }
        return 1;
    }
    public int ForumUpdatePostOperation(int userId, int forId, int disId, int posId) {
        // You can update the post if its yours or you are staff.
            if (!existMember(userId)) {
                return 0;
            } else {
                if (!existForum(forId)) {
                    return 0;
                } else {
                    if (!getForum(forId).existDiscussion(disId) ) {
                        return 0;
                    } else {
                        if(!getForum(forId).getDiscussion(disId).existPost(posId)) {
                            return 0;
                        } else {
                            if (userId == getForum(forId).getDiscussion(disId).getPost(posId).getOwner()){
                                getForum(forId).getDiscussion(disId).getPost(posId).incValue();
                            } else if (getMember(userId).getRole().equals("staff")) {
                                getForum(forId).getDiscussion(disId).getPost(posId).incValue();
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            }
        return 1;
    }
    public int ForumUserReportOperation(int userId, int forId, int disId, int posId) {
        // Any user can report a post.
            if (!existMember(userId)) {
                return 0;
            } else {
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
            }
        return 1;
    }
    public int ForumSubscribeOperation( int userId, int forId) {
        // Any user can report a post.
            if (!existMember(userId)) {
                return 0;
            } else {
                if (!existForum(forId)) {
                    return 0;
                } else {
                    if (getForum(forId).existSubscriber(userId)){
                        return 0;
                    } else {
                        getForum(forId).addSubscriber(userId);
                    }
                }
            }
        return 1;
    }
    public int ForumUnsubscribeOperation(int userId,int forId) {
        // Any user can report a post.
            if (!existMember(userId)) {
                return 0;
            } else {
                if (!existForum(forId)) {
                    return 0;
                } else {
                    if (!getForum(forId).existSubscriber(userId)){
                        return 0;
                    } else {
                        getForum(forId).deleteSubscriber(userId);
                    }
                }
            }
        return 1;
    }
    public int ForumSearchOperation(int userId){
        if (existMember(userId)){
            viewForumSearch();
        } else {
            return 0;
        }
        return 1;
    }
    public int ForumViewOperation(int userId, int forId){
        if (existMember(userId)){
            if (existForum(forId)){
                viewForum(forId);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
        return 1;
    }
    public int ForumViewAllOperation(int userId){
        if (existMember(userId)){
            viewForumAll();
        } else {
            return 0;
        }
        return 1;
    }
    public int ForumViewDiscussionOperation(int userId, int forId, int disId){
        if (existMember(userId) && existForum(forId)){
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

    public int ResourceAddOperation(int userId, int resId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (existResource(resId)) {
                return 0;
            }
            else {
                addResource(resId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int ResourceViewOperation(int userId, int resId){
        if(existMember(userId)) {
            if (!existResource(resId)) {
                return 0;
            }
            else {
                viewResource(resId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int ResourceViewAllOperation(int userId){
        if(existMember(userId)) {
            viewAllResource();
        } else {
            return 0;
        }
        
        return 1;
    }
    public int ResourceDeleteOperation(int userId, int resId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existResource(resId)) {
                return 0;
            }
            else {
                deleteResource(resId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int ResourceEditOperation(int userId, int resId){
        if(existMember(userId) &&getMember(userId).getRole().equals("staff")) {
            if (!existResource(resId)) {
                return 0;
            }
            else {
                editResource(resId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }

    public int UrlAddOperation(int userId, int modId, int secId, int urlId) {
       if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                if (getModule(modId).existSection(secId)){
                    if (getModule(modId).getSection(secId).existUrl(urlId)){
                        return 0;
                    } else {
                        getModule(modId).getSection(secId).addUrl(urlId);
                    }
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int UrlEditOperation(int userId, int modId, int secId, int urlId) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                if (getModule(modId).existSection(secId)){
                    if (!getModule(modId).getSection(secId).existUrl(urlId)){
                        return 0;
                    } else {
                        getModule(modId).getSection(secId).getUrl(urlId).incValue();
                    }
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        
        return 1;
    }    
    public int UrlDeleteOperation(int userId, int modId, int secId, int urlId) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (existModule(modId) && getModule(modId).existSection(secId) && getModule(modId).getSection(secId).existUrl(urlId)) {
                getModule(modId).getSection(secId).deleteUrl(urlId);
            } else{
                return 0;
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int UrlViewOperation(int userId, int modId, int secId, int urlId) {
        if(existMember(userId) ) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                if (getModule(modId).existSection(secId)){
                    if (!getModule(modId).getSection(secId).existUrl(urlId)){
                        return 0;
                    } else {
                        getModule(modId).getSection(secId).viewUrl(urlId);
                    }
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        
        return 1;
    }

    public int PageAddOperation(int userId, int pageId){
            if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                if (!existPage(pageId)){
                    addPage(pageId);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int PageViewOperation(int userId, int pageId){
        
            if (existMember(userId)) {
                if (existPage(pageId)){
                    viewPage(pageId);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;    
    
    }   
    public int PageEditOperation(int userId, int pageId){
            if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                if (existPage(pageId)){
                    getPage(pageId).incValue();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int PageDeleteOperation(int userId, int pageId){
            if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                if (existPage(pageId)){
                    deletePage(pageId);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    
    public int CourseModuleAddOperation(int userId, int modId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (existModule(modId)) {
                return 0;
            }
            else {
                addModule(modId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int CourseModuleEditOperation(int userId, int modId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                editModule(modId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int CourseModuleDeleteOperation(int userId, int modId){
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                deleteModule(modId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    public int CourseModuleViewOperation(int userId, int modId){
        if(existMember(userId)) {
            if (!existModule(modId)) {
                return 0;
            }
            else {
                viewModule(modId);
            }
        } else {
            return 0;
        }
        
        return 1;
    }
    
    public int CourseAddSectionOperation (int userId, int modId, int secId) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
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
        } else {
            return 0;
        }
        
        return 1;
    
    }
    public int CourseEditSectionOperation (int userId, int modId, int secId) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
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
        } else {
            return 0;
        }
        
        return 1;
    
    }
    public int CourseDeleteSectionOperation (int userId, int modId, int secId) {
        if(existMember(userId) && getMember(userId).getRole().equals("staff")) {
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
        } else {
            return 0;
        }
        
        return 1;
    
    }
    public int CourseViewSectionOperation (int userId, int modId, int secId) {
        if(existMember(userId) ) {
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
        } else {
            return 0;
        }
        
        return 1;
    
    }
    
    public int CourseEnrollOperation (Users us, int userId) {
        // Check that userId exists in Users
        // Check that courseId exists in Courses
        // Check that userId is not enrolled in courseId
        // Check that there are still places in the course.
        if (!us.existUser(userId)){
            return 0;
        } else {
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
            }
        
        return 1;
    }
    public int CourseEnrollGuestOperation (Users us, int userId) {
        // Check that userId exists in Users
        // Check that courseId exists in Courses
        // Check that userId is not enrolled in courseId
        // Check that there are still places in the course.
        if (!us.existUser(userId)){
            return 0;
        } else {
            if (existMember(userId)) {
                    return 0;
                } else {
                    if (getEnrollmentLimit() > 0 ) {
                    // enroll him!
                        addMember(userId, "guest");
                    } else {
                        return 0;
                    }
                }
            }
        
        return 1;
    }
    public int CourseUnenrollOperation (int userId) {
        // Check that userId exists in Users
        // Check that courseId exists in Courses
        // Check that userId is enrolled in courseId
        // Check that there is at least one staff left
        // Check that there are still places in the course.
            if (!existMember(userId)) {
                    return 0;
                } else {
                    if (getMember(userId).getRole().equals("staff") ) {
                        if (countMemberType("staff") > 1)
                            deleteMember(userId);
                        else {
                            return 0;
                        }
                    } else {
                        deleteMember(userId);
                    }
                }
        
        return 1;
    }
    public int CourseEditOperation (int userId) {
            if (!existMember(userId)) {
                    return 0;
                } else {
                    if (getMember(userId).getRole().equals("staff") ) {
                        incValue();
                    } else {
                        return 0;
                    }
                }
        
        return 1;
    }
    public int CourseViewOperation(Users us, int userId) {
        // Maybe it shouldn't need to check the user!!
        if (us.existUser(userId)){
            viewCourse();
        } else {
            return 0;
        }
        return 1;
    }
    public int CourseReportLogOperation(int userId) {
        if (existMember(userId) && getMember(userId).getRole().equals("staff")){
            viewCourseReport();
        } else {
            return 0;
        }
        return 1;
    }
    public int CourseReportOutlineOperation(int userId) {
        if (existMember(userId) && getMember(userId).getRole().equals("staff")){
            viewCourseReportOutline();
        } else {
            return 0;
        }
        return 1;
    }
    public int CourseRoleAssignOperation (int userId, int promoteId) {
            if (existMember(userId)){
                if (getMember(userId).getRole().equals("staff")) {
                    if (existMember(promoteId)){
                        if (!getMember(promoteId).getRole().equals("staff")) {
                            getMember(promoteId).setOldRole(getMember(promoteId).getRole());
                            getMember(promoteId).setRole("staff");
                            // many things can go wrong now. because he is not and student any more he may loose access to the quizes.
                            // SHould we fix that here??
                        } else {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int CourseRoleUnassignOperation (int userId, int demoteId) {
            if (existMember(userId)){
                if (getMember(userId).getRole().equals("staff")) {
                    if (existMember(demoteId)){
                        if (getMember(demoteId).getRole().equals("staff")) {
                            if (countMemberType("staff") > 1) { 
                                getMember(demoteId).setRole(getMember(demoteId).getOldRole());
                            } else {
                                return 0;
                            }
                        } else {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }

    
    public int QuizAddOperation(int userId, int quizId, int maxAttemps){
    
        if (existMember(userId) && getMember(userId).getRole().equals("staff")){
            if (existQuiz(quizId)) {
                return 0;
            } else {
                addQuiz(quizId, maxAttemps);
            }
        } else {
            return 0;
        }
        return 1;
    }
    public int QuizAttemptOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("student") ) {
                    if (existQuiz(quizId)) {
                        if (getQuiz(quizId).getQuizStudent(userId).getOpen()) {
                            return 0;
                        } else {
                            if (getQuiz(quizId).getQuizStudent(userId).getStopped()) {
                                // If it has been stopped you have to continue it, no attemp again.
                                return 0;
                            } else {
                                if (getQuiz(quizId).getQuizStudent(userId).getAttemps() > 0 ) {
                                    // You may attemp it.
                                    getQuiz(quizId).getQuizStudent(userId).setOpen(TRUE);
                                    getQuiz(quizId).getQuizStudent(userId).decAttemps();
                                } else {
                                    return 0;
                                }
                            }
                        }
                    } else {
                        return 0;
                    }    
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizCloseAttemptOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("student") ) {
                    if (existQuiz(quizId)) {
                        if (!getQuiz(quizId).getQuizStudent(userId).getOpen()) {
                            return 0;
                        } else {
                            if (getQuiz(quizId).getQuizStudent(userId).getStopped()) {
                                // If it has been stopped you have to continue it, no attemp again.
                                return 0;
                            } else {
                                getQuiz(quizId).getQuizStudent(userId).setOpen(FALSE);
                                getQuiz(quizId).getQuizStudent(userId).setStopped(TRUE);
                            }
                        }
                    } else {
                        return 0;
                    }    
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizContinueAttemptOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("student") ) {
                    if (existQuiz(quizId)) {
                        if (!getQuiz(quizId).getQuizStudent(userId).getOpen()) {
                            return 0;
                        } else {
                            if (!getQuiz(quizId).getQuizStudent(userId).getStopped()) {
                                return 0;
                            } else {
                                getQuiz(quizId).getQuizStudent(userId).setOpen(TRUE);
                                getQuiz(quizId).getQuizStudent(userId).setStopped(FALSE);
                            }
                        }
                    } else {
                        return 0;
                    }    
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizStopAttemptOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("student") ) {
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
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizEditOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
         if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (existQuiz(quizId)) {
                        getQuiz(quizId).incValue();
                    } else {
                        return 0;
                    }    
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizManualGradeOperation(int userId, int quizId, int studentId, int grade) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (existQuiz(quizId)) {
                        // Should it be open or closed???
                        // Should we close the exam??
                        getQuiz(quizId).getQuizStudent(studentId).setGrade(grade);
                    } else {
                        return 0;
                    }    
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizPreviewOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (existQuiz(quizId)) {
                       viewQuizPreview();
                    } else {
                        return 0;
                    }    
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizReportOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (existQuiz(quizId)) {
                   viewQuizReport();
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizViewOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (existQuiz(quizId)) {
                    viewQuiz();
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizViewAllOperation(int userId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                       viewQuizAll();
                } else {
                    return 0;
                }
            }
        return 1;
    }
    public int QuizViewSummaryOperation(int userId, int quizId) {
        // User has to be a student to attempt the quiz.
        // We are not checking that the QUIZ is not open by other users.
        if (!existMember(userId)) {
                return 0;
            } else {
                if (getMember(userId).getRole().equals("staff") ) {
                    if (existQuiz(quizId)) {
                       viewQuizSummary();
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        return 1;
    }
    
    public int AssignmentAddOperation(int userId, int assId) {
        if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                if (!existAssignment(assId)) {
                    addAssignment(assId);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentUpdateOperation(int userId, int assId) {
        if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                if (existAssignment(assId)) {
                        getAssignment(assId).incValue();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentUpdateSubmissionOperation(int userId, int assId) {
        if (existMember(userId) && getMember(userId).getRole().equals("student")) {
                if (existAssignment(assId)) {
                    if (getAssignment(assId).existSubmission(userId)){
                        getAssignment(assId).getSubmission(userId).incValue();
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentUpdateGradesOperation(int userId, int assId, int studentId, int grade) {
        if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                if (existAssignment(assId)) {
                    if (getAssignment(assId).existSubmission(studentId)) {
                        getAssignment(assId).getSubmission(studentId).setGrade(grade);
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentUploadOperation(int userId, int assId) {
            if (existMember(userId) && getMember(userId).getRole().equals("student")) {
                if (existAssignment(assId)) {
                    getAssignment(assId).addSubmission(userId);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentViewSubmissionOperation(int userId, int assId) {
            if (existMember(userId) && getMember(userId).getRole().equals("student")) {
                if (existAssignment(assId)) {
                    if (getAssignment(assId).existSubmission(userId)) {
                        getAssignment(assId).viewSubmission(userId);
                    }else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentViewAllOperation(int userId) {
            if (existMember(userId) && getMember(userId).getRole().equals("staff")) {
                for (Assignment a: getAssignments()){
                    // We view all the submissions.
                    a.viewSubmission(userId);
                }
            } else {
                return 0;
            }
        return 1;
    }
    public int AssignmentViewOperation(int userId, int assId) {
            if (existMember(userId)) {
                if (existAssignment(assId)) {
                    getAssignment(assId).viewAssignment();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        return 1;
    }
    
    
}



