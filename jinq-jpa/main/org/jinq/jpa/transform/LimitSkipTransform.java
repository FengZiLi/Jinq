package org.jinq.jpa.transform;

import org.jinq.jpa.MetamodelUtil;
import org.jinq.jpa.jpqlquery.JPQLQuery;
import org.jinq.jpa.jpqlquery.SelectFromWhere;

public class LimitSkipTransform extends JPQLNoLambdaQueryTransform
{
   public LimitSkipTransform(MetamodelUtil metamodel, ClassLoader alternateClassLoader, boolean isLimit, long n)
   {
      super(metamodel, alternateClassLoader);
      constraint = n;
      this.isLimit = isLimit;
   }
   
   boolean isLimit;
   long constraint;
   
   @Override
   public <U, V> JPQLQuery<U> apply(JPQLQuery<V> query) throws QueryTransformException
   {
      if (query instanceof SelectFromWhere)
      {
         SelectFromWhere<V> sfw = (SelectFromWhere<V>)query;
         
         if (isLimit && sfw.limit >= 0) throw new IllegalArgumentException("Cannot limit a query more than once");
         if (!isLimit && sfw.skip >= 0) throw new IllegalArgumentException("Cannot skip in a query more than once");
         
         // Create the new query, merging in the analysis of the method
         SelectFromWhere<U> toReturn = (SelectFromWhere<U>)sfw.shallowCopy();
         
         if (isLimit)
         {
            toReturn.limit = constraint;
         }
         else
         {
            if (toReturn.limit >= 0) toReturn.limit -= constraint;
            toReturn.skip = constraint;
         }

         return toReturn;
      }
      throw new QueryTransformException("Existing query cannot be transformed further");
   }

}
