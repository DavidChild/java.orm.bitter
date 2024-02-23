package io.github.davidchild.bitter.basequery;

public class BaseExecute   {
    private  ISingleQuery singleQuery;

    public ISingleQuery getSingleQuery(){
        return  singleQuery;
    }

    protected void setQuery(ISingleQuery query){
        singleQuery = query;
    }

}
