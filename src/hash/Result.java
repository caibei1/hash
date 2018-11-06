package hash;

import java.util.LinkedList;
import java.util.List;

public class Result {

	private int sum = 1;
	private Integer zero;
	private Integer one;
	private Integer two;
	private Integer three;
	private Integer fore;
	private Integer five;
	private Integer six;
	private Integer seven;
	private String threadName;
	
	
	public Integer getZero() {
		return zero;
	}

	public void setZero(Integer zero) {
		this.zero = zero;
	}

	public Integer getOne() {
		return one;
	}

	public void setOne(Integer one) {
		this.one = one;
	}

	public Integer getTwo() {
		return two;
	}

	public void setTwo(Integer two) {
		this.two = two;
	}

	public Integer getThree() {
		return three;
	}

	public void setThree(Integer three) {
		this.three = three;
	}

	public Integer getFore() {
		return fore;
	}

	public void setFore(Integer fore) {
		this.fore = fore;
	}

	public Integer getFive() {
		return five;
	}

	public void setFive(Integer five) {
		this.five = five;
	}

	public Integer getSix() {
		return six;
	}

	public void setSix(Integer six) {
		this.six = six;
	}

	public Integer getSeven() {
		return seven;
	}

	public void setSeven(Integer seven) {
		this.seven = seven;
	}

	

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}


	public  int findZero(Result r,String result){
		if(!result.substring(0,1).equals("0")){
			r.setZero(r.getZero()+1);
			return 0;
		}
		else if(result.substring(0,1).equals("0")&&!result.substring(1,2).equals("0")){
			r.setOne(r.getOne()+1);
			return 1;
		}
		else if(result.substring(0,1).equals("0")&&result.substring(1,2).equals("0")&&!result.substring(2,3).equals("0")){
			r.setTwo(r.getTwo()+1);
			return 2;
		}
		else if(result.substring(0,1).equals("0")&&result.substring(1,2).equals("0")&&result.substring(2,3).equals("0")
				&&!result.substring(3,4).equals("0")){
			r.setThree(r.getThree()+1);
			return 3;
			//hashList.add(result);
		}
		else if(result.substring(0,1).equals("0")&&result.substring(1,2).equals("0")&&result.substring(2,3).equals("0")
				&&result.substring(3,4).equals("0")&&!result.substring(4,5).equals("0")){
			r.setFore(r.getFore()+1);
			return 4;
			//System.out.println(result);
			//hashList.add(result);
		}
		else if(result.substring(0,1).equals("0")&&result.substring(1,2).equals("0")&&result.substring(2,3).equals("0")
				&&result.substring(3,4).equals("0")&&result.substring(4,5).equals("0")&&!result.substring(5,6).equals("0")){
			r.setFive(r.getFive()+1);
			return 5;
			//System.out.println(result);
			//hashList.add(result);
		}
		else if(result.substring(0,1).equals("0")&&result.substring(1,2).equals("0")&&result.substring(2,3).equals("0")
				&&result.substring(3,4).equals("0")&&result.substring(4,5).equals("0")&&result.substring(5,6).equals("0")
				&&!result.substring(6,7).equals("0")){
			r.setSix(r.getSix()+1);
			return 6;
			//hashList.add(result);
		}
		else if(result.substring(0,1).equals("0")&&result.substring(1,2).equals("0")&&result.substring(2,3).equals("0")
				&&result.substring(3,4).equals("0")&&result.substring(4,5).equals("0")&&result.substring(5,6).equals("0")
				&&result.substring(6,7).equals("0")&&!result.substring(7,8).equals("0")){
			r.setSeven(r.getSeven()+1);
			return 7;
			//hashList.add(result);
		}
		else{
			System.out.println("存在大于等于8个0的情况");
			//hashList.add(result);
			return 8;
		}
	}


	@Override
	public String toString() {
		return "Result [zero=" + zero/sum + ", one=" + one/sum + ", two=" + two/sum + ", three=" + three/sum + ", four=" + fore/sum
				+ ", five=" + five/sum + ", six=" + six/sum + ", seven=" + seven/sum + ",  threadName="
				+ threadName + "]";
	}
}
