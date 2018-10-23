package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPoligon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import math.util.Vector3;

public class FilledPoligonTool implements Tool {
	private Point start;
	private Point point;
	private List<Point> list = new ArrayList<>();
	private DrawingModel dm;
	private int counter = 0;
	private JColorArea jca1;
	private JColorArea jca2;

	public FilledPoligonTool(DrawingModel model, JColorArea fgColorArea, JColorArea bgColorArea) {
		this.dm = model;
		this.jca1 = fgColorArea;
		this.jca2 = bgColorArea;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			list = new ArrayList<>();
			counter = 0;
		} else {
			if (list.size() == 0) {
				start = e.getPoint();
				System.out.println(start);
				list.add(counter, start);
				counter++;
			} else {
				point = e.getPoint();
				double distance = Math.hypot(start.x - point.x, start.y - point.y);
				double distance2 = Math.hypot(list.get(counter - 1).x - point.x, list.get(counter - 1).y - point.y);
				if (distance < 3 && counter >= 2) {
					list.add(counter, point);
					dm.add(new FilledPoligon(jca1.getCurrentColor(), jca2.getCurrentColor(), list));
					list = new ArrayList<>();
					counter = 0;
				} else if (distance > 3 && distance2 > 3) {
					List<Vector3> vectors = new ArrayList<>();
					for(int j = 0 ; j<list.size();j++) {
						if(j==list.size()-1) {
							vectors.add(new Vector3(point.x-list.get(j).x, point.y-list.get(j).y, 0));
						}else {
							vectors.add(new Vector3(list.get(j+1).x-list.get(j).x, list.get(j+1).y-list.get(j).y, 0));
						}
					}
					vectors.add(new Vector3(list.get(0).x-point.x, list.get(0).y-point.y, 0));
					boolean set = true;
					for(int i = 0 ; i< vectors.size();i++) {
						if(i==vectors.size()-1) {
							if(vectors.get(i).cross(vectors.get(0)).getZ() < 0) {
								set = false;
							}
						}else {
							if(vectors.get(i).cross(vectors.get(i+1)).getZ() < 0){
								set = false;
							}
						}
						
					} if (set) {
					list.add(counter, point);
					counter++;
					} else {
						System.out.println("Ova tocka bi stvorila konveksni poligon");
					}
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (counter > 0) {
			point = e.getPoint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (counter == 1) {
			Line line = new Line((int) start.getX(), (int) start.getY(), (int) point.getX(), (int) point.getY(),
					jca1.getCurrentColor());
			g2d.setColor(line.getColor());
			g2d.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
		} else if (counter >= 2) {
				int[] xPoints = new int[list.size() + 1];
			int[] yPoints = new int[list.size() + 1];
			int i = 0;
			for (Point p : list) {
				xPoints[i] = p.x;
				yPoints[i] = p.y;
				i++;
			}
			xPoints[i] = point.x;
			yPoints[i] = point.y;
			i++;
			g2d.setColor(jca2.getCurrentColor());
			g2d.fillPolygon(xPoints, yPoints, i);
			g2d.setColor(jca1.getCurrentColor());
			g2d.drawPolygon(xPoints, yPoints, i);
//			}else {
//				System.out.println("Korisnice ova tocka bi stvorila ne konveksni poligon");
//			}
			
		}

	}

}
