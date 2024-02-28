package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.functional.IfInnerLambda;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.parse.BitterPredicate;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.List;

public interface IWhereQuery<OP extends BaseQuery,T extends BaseModel>  {
    public default OP where(BitterPredicate<T> predicate) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        iBagWhere.getWhereContainer().getPredicateCondition().add(predicate);
        return (OP)this;
    }


    /** string **/
    public  default OP where(String setWhere, Object... args) {
       IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
       SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
       subWhereStatement.custom(setWhere,args);
       iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
       return  (OP)this;
    }


    public  default OP where(String setWhere) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.custom(setWhere,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }



    public  default OP whereNotNull(String setWhere, Object arg) {
        if (CoreStringUtils.isNotNull(arg)) {
            where(setWhere,arg);
        }
        return  (OP)this;
    }

    public  default OP whereNotEmpty(String setWhere, Object arg) {
        if (arg != null && CoreStringUtils.isNotEmpty(String.valueOf(arg))) {
            where(setWhere,arg);
        }
        return  (OP)this;
    }


    public  default OP whereNotEmpty(IfInnerLambda ifInnerLambda,String setWhere, String arg){
        if(ifInnerLambda.IfOrNot()){
            whereNotEmpty(setWhere,arg);
        }
        return (OP)this;
    }

    public  default OP where(IfInnerLambda ifInnerLambda,String setWhere, Object... args){
        if(ifInnerLambda.IfOrNot()){
            where(setWhere,args);
        }
        return (OP)this;
    }

    public  default OP where(IfInnerLambda ifInnerLambda,String setWhere) {
        if(ifInnerLambda.IfOrNot()){
            where(setWhere);
        }
        return (OP)this;
    }
    public  default OP whereNotNull(IfInnerLambda ifInnerLambda,String setWhere, Object arg) {
        if(ifInnerLambda.IfOrNot()){
            whereNotNull(setWhere,arg);
        }
        return (OP)this;
    }



    /*** eq **/
    public  default OP whereEq(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.eq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereEq(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereEq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEq(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.eq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereEq(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereEq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEqNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereEq(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereEqNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereEq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereEqNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereEq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereEqNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereEq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereEqNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEqNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEqNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEqNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEqNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEqNotNull(field,value);
        }
        return (OP)this;
    }


    /*** neq **/
    public  default OP whereNeq(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.neq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereNeq(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereNeq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereNeq(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.neq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereNeq(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereNeq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereNeqNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereNeq(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereNeqNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereNeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereNeqNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereNeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereNeqNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereNeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereNeqNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereNeqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereNeqNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereNeqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereNeqNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereNeqNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereNeqNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereNeqNotNull(field,value);
        }
        return (OP)this;
    }



    /*** lt **/
    public  default OP whereLt(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.lt(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereLt(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereLt(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLt(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.lt(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereLt(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereLt(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLtNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereLt(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereLtNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereLt(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereLtNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereLt(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereLtNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereLt(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereLtNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLtNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLtNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLtNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLtNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLtNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLtNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLtNotNull(field,value);
        }
        return (OP)this;
    }




    /*** leq **/
    public  default OP whereLeq(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.leq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereLeq(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereLeq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLeq(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.leq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereLeq(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereLeq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLeqNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereLeq(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereLeqNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereLeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereLeqNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereLeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereLeqNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereLeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereLeqNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLeqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLeqNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLeqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLeqNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLeqNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereLeqNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereLeqNotNull(field,value);
        }
        return (OP)this;
    }




    /*** gt **/
    public  default OP whereGt(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.gt(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereGt(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereGt(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGt(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.gt(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereGt(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereGt(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGtNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereGt(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereGtNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereGt(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereGtNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereGt(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereGtNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereGt(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereGtNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGtNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGtNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGtNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGtNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGtNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGtNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGtNotNull(field,value);
        }
        return (OP)this;
    }


    /*** geq **/
    public  default OP whereGeq(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.geq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereGeq(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereGeq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGeq(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.geq(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereGeq(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereGeq(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGeqNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereGeq(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereGeqNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereGeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereGeqNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereGeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereGeqNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereGeq(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereGeqNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGeqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGeqNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGeqNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGeqNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGeqNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereGeqNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereGeqNotNull(field,value);
        }
        return (OP)this;
    }



    /*** allLike **/
    public  default OP whereAllLike(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.allLike(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereAllLike(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereAllLike(field,value);
        }
        return (OP)this;
    }
    public  default OP whereAllLike(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.allLike(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereAllLike(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereAllLike(field,value);
        }
        return (OP)this;
    }
    public  default OP whereAllLikeNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereAllLike(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereAllLikeNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereAllLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereAllLikeNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereAllLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereAllLikeNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereAllLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereAllLikeNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereAllLikeNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereAllLikeNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereAllLikeNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereAllLikeNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereAllLikeNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereAllLikeNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereAllLikeNotNull(field,value);
        }
        return (OP)this;
    }


    /*** preLike **/
    public  default OP wherePreLike(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.preLike(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP wherePreLike(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            wherePreLike(field,value);
        }
        return (OP)this;
    }
    public  default OP wherePreLike(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.preLike(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP wherePreLike(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            wherePreLike(field,value);
        }
        return (OP)this;
    }
    public  default OP wherePreLikeNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            wherePreLike(field,value);
        }
        return  (OP)this;
    }
    public  default OP wherePreLikeNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            wherePreLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP wherePreLikeNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            wherePreLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP wherePreLikeNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            wherePreLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP wherePreLikeNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            wherePreLikeNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP wherePreLikeNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            wherePreLikeNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP wherePreLikeNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            wherePreLikeNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP wherePreLikeNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            wherePreLikeNotNull(field,value);
        }
        return (OP)this;
    }



    /*** endLike **/
    public  default OP whereEndLike(String field,Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.endLike(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereEndLike(IfInnerLambda ifInnerLambda,String field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereEndLike(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEndLike(FieldFunction<T> field, Object value) {
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.endLike(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereEndLike(IfInnerLambda ifInnerLambda,FieldFunction<T> field,Object value) {
        if(ifInnerLambda.IfOrNot()){
            whereEndLike(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEndLikeNotNull(String field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereEndLike(field,value);
        }
        return  (OP)this;
    }
    public  default OP whereEndLikeNotNull(FieldFunction<T> field,Object value) {
        if (CoreStringUtils.isNotNull(value)) {
            whereEndLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereEndLikeNotEmpty(FieldFunction<T> field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereEndLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereEndLikeNotEmpty(String field,Object value) {
        if (value != null && CoreStringUtils.isNotEmpty(String.valueOf(value))) {
            whereEndLike(field,value);
        }
        return  (OP)this;
    }

    public  default OP whereEndLikeNotEmpty(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEndLikeNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEndLikeNotEmpty(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEndLikeNotEmpty(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEndLikeNotNull(IfInnerLambda ifInnerLambda,String field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEndLikeNotNull(field,value);
        }
        return (OP)this;
    }
    public  default OP whereEndLikeNotNull(IfInnerLambda  ifInnerLambda,FieldFunction<T> field,Object value){
        if(ifInnerLambda.IfOrNot()){
            whereEndLikeNotNull(field,value);
        }
        return (OP)this;
    }



    /*** in **/
    public  default OP whereIn(String field, List value) {
        if(value == null || value.size() <1) {
            return  (OP)this;
        }
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.in(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereIn(IfInnerLambda ifInnerLambda,String field,List value) {
        if(ifInnerLambda.IfOrNot()){
            whereIn(field,value);
        }
        return (OP)this;
    }
    public  default OP whereIn(FieldFunction<T> field, List value) {
        if(value == null || value.size() <1) {
            return  (OP)this;
        }
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.in(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereIn(IfInnerLambda ifInnerLambda,FieldFunction<T> field,List value) {
        if(ifInnerLambda.IfOrNot()){
            whereIn(field,value);
        }
        return (OP)this;
    }



    /*** notIn **/
    public  default OP whereNotIn(String field,List value) {
        if(value == null || value.size() <1) {
            return  (OP)this;
        }
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.notIn(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereNotIn(IfInnerLambda ifInnerLambda,String field,List value) {
        if(ifInnerLambda.IfOrNot()){
            whereNotIn(field,value);
        }
        return (OP)this;
    }
    public  default OP whereNotIn(FieldFunction<T> field, List value) {
        if(value == null || value.size() <1) {
            return  (OP)this;
        }
        IBagWhere iBagWhere = ((IBagWhere)((OP)this).getExecuteParBag());
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.notIn(field,value,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }
    public  default OP whereNotIn(IfInnerLambda ifInnerLambda,FieldFunction<T> field,List value) {
        if(ifInnerLambda.IfOrNot()){
            whereNotIn(field,value);
        }
        return (OP)this;
    }
}
