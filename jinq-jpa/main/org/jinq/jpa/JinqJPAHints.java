package org.jinq.jpa;

class JinqJPAHints
{
   public int automaticResultsPagingSize = 10000;
   public JPAQueryLogger queryLogger = null;
   
   public void setHint(String name, Object val)
   {
      if ("automaticPageSize".equals(name) && val instanceof Integer)
         automaticResultsPagingSize = (int)val;
      if ("queryLogger".equals(name) && val instanceof JPAQueryLogger)
         queryLogger = (JPAQueryLogger)val;
   }
}