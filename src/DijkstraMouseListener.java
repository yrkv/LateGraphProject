import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

public class DijkstraMouseListener implements MouseListener {
    private Set<DijkstraDemo.Node> nodes;

    public DijkstraMouseListener(Set<DijkstraDemo.Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Update the state of the start or end node and update the graph when one is clicked.
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // I know this is inefficient, but it's fine for small graphs, and large graphs can't be rendered well anyway.

        for (DijkstraDemo.Node node: nodes) {
            double dist = node.getLocation().distance(mouseEvent.getPoint());

            if (dist < DijkstraDemo.Node.RADUIS) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    DijkstraDemo.updateStart(node);
                }

                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    DijkstraDemo.updateEnd(node);
                }
            }
        }
    }

    // The rest of the methods are irrelevant; We just need them to be implemented even if they do nothing.

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
