package irg;

public class Demo4 {

	public static void main(String[] args) {
		
		Vector3 o = new Vector3(3.9, -4.0, -10.4);
		
		Vector3 va = new Vector3(-7.5, -1.5, -3.0);
		Vector3 vb = new Vector3(-9.1, -6.5, -7.5);
		Vector3 vc = new Vector3(-1.0, -2.5, 8.1);
		Vector3 vd = new Vector3(-8.6, 4.2, 4.0);
		
		Vector3 vab = vb.sub(va);
		Vector3 vac = vc.sub(va);
		Vector3 n1 = vab.cross(vac);
		double d1 = -n1.dot(va);
		System.out.println(n1.dot(o) + d1);
		
		Vector3 vbd = vd.sub(vb);
		Vector3 vbc = vc.sub(vb);
		Vector3 n2 = vbd.cross(vbc);
		double d2 = -n2.dot(vb);
		System.out.println(n2.dot(o) + d2);
		
		Vector3 vcd = vd.sub(vc);
		Vector3 vca = va.sub(vc);
		Vector3 n3 = vcd.cross(vca);
		double d3 = -n3.dot(vc);
		System.out.println(n3.dot(o) + d3);
		
		Vector3 vad = vd.sub(va);
//		Vector3 vab = vb.sub(va);
		Vector3 n4 = vad.cross(vab);
		double d4 = -n4.dot(vd);
		System.out.println(n4.dot(o) + d4);
		
//		System.out.println(n1);
//		Vector3 o1 = va.sub(o);
//		System.out.println(n1.cosAngle(o1));
	}
}
