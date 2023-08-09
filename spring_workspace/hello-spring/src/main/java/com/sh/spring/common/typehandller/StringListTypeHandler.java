package com.sh.spring.common.typehandller;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {
   
   // 자바에서 List<>와 DB에서 varchar 타입의 형이 다르기 때문에
   // 타입 핸들러 클래스가 필요함
   
   @Override
   public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
         throws SQLException {
      String value = "";
      for(int j = 0; j < parameter.size(); j++) {
         value += parameter.get(j);
         if(j != parameter.size()-1) 
            value += ", ";
      }
      ps.setString(i,  value);
   }
   

   @Override
   public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
      String value = rs.getString(columnName);
      if(value != null) {
         String[] values = value.split(",");
         return Arrays.asList(values);
      }
      return null;
   }
   

   @Override
   public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
      String value = rs.getString(columnIndex);
      if(value != null) {
         String[] values = value.split(",");
         return Arrays.asList(values);
      }
      return null;
   }
   

   @Override
   public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
      String value = cs.getString(columnIndex);
      if(value != null) {
         String[] values = value.split(",");
         return Arrays.asList(values);
      }
      return null;
   }

}