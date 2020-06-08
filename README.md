### (Very) Late API Project
---

I chose the fairly straightforward [jgrapht](https://jgrapht.org/) library. The website describes it well, but put very simply it provides some conveniences for programming anything which involves graph structures. This (the one you are reading the readme for) project will consist of two main demos, each of which heavily relies on the JGraphT library. The first shows off a large number of small scale features, while the second integrates it into a larger, cohesive demo.

---

#### Words and Such

We can't show off how cool a graph library is without a graph. I happened to be helping a friend at the University of Iowa with a problem involving a graph and it happens to serve as a decent demo for a few features of JGraphT. The graph is defined as containing each word in a dictionary as a node, with there being an edge between words if they are one character replacement different. The graph does not contain fake words. We only deal with real words here. None of that "other languages" nonsense, only _American_ here. Pictured is a small snapshot of the graph centered on 'chair'. No other edges connect any of these words further. This is an isolated "island" in the graph.

![didn't think you'd look at this text](chair.jpg)

What fun may be had with this large graph? We can do things like search for the shortest distance between two words (if they're even connected), look for the furthest word from any given word, identify the number of disconnected subgraphs exist and how large they are. Running `WordDemo` does all of these.

---

### Interactive _Fun_

This next part is simple: run `DijkstraDemo` and you'll see a weighted graph on the screen. Left click sets the starting node and right click sets the destination. JGraphT will run Dijkstra's on the graph and highlight the shortest route. If it doesn't work for you, blame inconsistencies across operating systems and imagine it working.

![image.jpg.png.pdf](dijkstraDemo.png)


