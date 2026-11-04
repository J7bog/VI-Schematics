package vischematics;

import arc.func.ConsT;
import arc.util.Http;
import arc.util.Http.*;

public class Supabase {
    private static final String projectId = "hiacmaksrirkpwnyaeqr";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhpYWNtYWtzcmlya3B3bnlhZXFyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTM2MTU2MzgsImV4cCI6MjEwOTE5MTYzOH0.jbyFUCm4gXny6w2yaqpmRTG3MXrZucqsJHyzkzmYa30";
    
    private static final String FUNCTIONS_URL = "https://" + projectId + ".supabase.co/rest/v1/rpc/";
    
    public static void invoke(String function, ConsT<HttpResponse, Exception> response, String... args) {
        request(function, args).submit(response);
    }
    
    private static HttpRequest request(String function, String[] args) {
        return Http.post(FUNCTIONS_URL + function)
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .header("Content-Type", "application/json")
            .content(toJson(args));
    }
    
    private static String toJson(String[] args) {
        var sb = new StringBuilder().append('{');
        for (int i = 0; i < args.length ; ) {
            addArgs(sb, args[i++], args[i++]);
            if (i < args.length) {
                sb.append(", ");
            } else {
                return sb.append('}').toString();
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    private static void addArgs(StringBuilder sb, String arg1, String arg2) {
        addArg(sb, arg1);
        sb.append(": ");
        addArg(sb, arg2);
    }
    
    private static void addArg(StringBuilder sb, String arg) {
        quote(sb, arg);
    }
    
    private static void quote(StringBuilder sb, String text) {
        sb.append('\"').append(text).append('\"');
    }
}