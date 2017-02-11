package cn.xiedacon.util.jdbc.setter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DateSetter extends DataSetter {

	@Override
	public void reallySet(PreparedStatement statement, int index, Object data) throws SQLException {
		statement.setDate(index, new Date(((java.util.Date) data).getTime()));
	}

}
