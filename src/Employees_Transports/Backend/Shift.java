package Employees_Transports.Backend;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shift {
	private Date _shiftDate;
	private boolean _isMorning;
	private station _station;

	public Shift(Date shiftDate, boolean isMorning, station station) {
		_shiftDate = shiftDate;
		_isMorning = isMorning;
		_station = station;
	}

	public Date getShiftDate() {
		return this._shiftDate;
	}

	public void setShiftDate(Date shiftDate) {
		this._shiftDate = shiftDate;
	}

	public boolean getIsMorning() {
		return this._isMorning;
	}
	
	public void setIsMorning(boolean isMorning) {
		_isMorning = isMorning;
	}
	
	public station getStation() {
		return this._station;
	}
	
	public void setStation(station s){
		_station = s;
	}
	
	public String toString(){
		String str = ""+ (new SimpleDateFormat("dd/MM/yyyy").format(_shiftDate));
		if(_isMorning) str= str+" Morning";
		else
			str= str+" Evening";
		str = str+ "\n"+ _station;
		return str;
	}
}