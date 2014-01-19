
public class LinkedDocumentCollection extends DocumentCollection {
	
	private final double delta = 0.0000000001;
	
	public LinkedDocumentCollection() {
		super();
	}

	@Override
	public void addFirst(Document doc) {
		if ((doc instanceof LinkedDocument) && !(this.contains(doc))) {
			super.addFirst(doc);
		}
	}

	@Override
	public void addLast(Document doc) {
		if ((doc instanceof LinkedDocument) && !(this.contains(doc))) {
			super.addLast(doc);
		}
	}

	private void crawl(LinkedDocumentCollection resultCollection) {
		if (this.size() == 0) {
			return;
		}

		/* loop over all documents of this collection and add them to
		 * the in/out parameter, if not already contained. */
		for (int i = 0; i < this.size(); i++) {
			LinkedDocument doc = (LinkedDocument) this.get(i);

			if (!resultCollection.contains(doc)) {
				resultCollection.addLast(doc);

				/* do the same recursively */
				doc.getOutgoingLinks().crawl(resultCollection);
			}
		}
	}

	public LinkedDocumentCollection crawl() {
		/* prepare the resulting collection and begin crawling ... */
		LinkedDocumentCollection resultCollection = new LinkedDocumentCollection();		
		this.crawl(resultCollection);
		return resultCollection;
	}

	public void calculateIncomingLinks() {
		/* loop over all documents in this collection */
		for (int i = 0; i < this.size(); i++) {
			LinkedDocument doc = (LinkedDocument) this.get(i);

			/* again, loop over all documents of this collection */
			for (int j = 0; j < this.size(); j++) {
				LinkedDocument incomingDoc = (LinkedDocument) this.get(j);

				/* Check if doc is contained in the outgoing links of incomingDoc.
				 * If so, add to incoming links of doc. */
				if (incomingDoc.getOutgoingLinks().contains(doc)) {
					doc.addIncomingLink(incomingDoc);
				}
			}
		}
	}
	
	public String toString() {
		if (this.size() == 0) {
			return "[]";
		}

		if (this.size() == 1) {
			return "[" + ((LinkedDocument) this.get(0)).getID() + "]";
		}

		String res = "[";
		for (int i = 0; i < this.size() - 1; i++) {
			res += ((LinkedDocument) this.get(i)).getID() + ", ";
		}
		res += ((LinkedDocument) this.get(this.size() - 1)).getID() + "]";
		return res;
	}
	
	private static double[] multiply(double[][] matrix, double[] vector)	{
		if ((matrix == null) || (vector == null))	{
			return null;
		}
		if (matrix.length != vector.length)	{
			return null;
		}
		
		double[] result = new double[vector.length];
		double sum = 0;
		
		for (int i = 0; i < matrix.length; i++)	{
			for (int j = 0; j < matrix[i].length; j++)	{
				sum += matrix[j][i] * vector[j];
			}
			
			result[i] = sum;
			sum = 0;
		}
		
		return result;
	}
	
	public double[] pageRank(double dampingFactor)	{
		if ((dampingFactor < 0) || (dampingFactor > 1))	{
			return null;
		}
		if (this.size() < 1)	{
			return null;
		}
		
		double[][] rankMatrix = new double[this.size()][this.size()];
		double[] vector = new double[this.size()];
		
		for (int i = 0; i < this.size(); i++)	{	//Go through all Documents of the LinkedDocumentCollection (this)
			for (int j = 0; j < this.size(); j++)	{	//Go through all OutgoingLinks of each LinkedDocument in the LinkedDocumentCollection (this)
				LinkedDocumentCollection tmp = ((LinkedDocument)this.get(i)).getOutgoingLinks();
				
					if (tmp.contains((LinkedDocument)this.get(j)))	{
						rankMatrix[i][j] = ((double)1 / tmp.size());
						
					}	else	{
						rankMatrix[i][j] = 0;
					}
			}
		}
		
		for (int i = 0; i < rankMatrix.length; i++)	{
			for (int j = 0; j < rankMatrix[i].length; j++)	{
				rankMatrix[i][j] = (dampingFactor * rankMatrix[i][j]) + ((1 - dampingFactor) / rankMatrix.length);
			}
		}
		
		for (int i = 0; i < vector.length; i++)	{
			vector[i] = ((double)1 / vector.length);
		}
		
		double[] oldVec;
		do	{
			oldVec = vector;
			vector = LinkedDocumentCollection.multiply(rankMatrix, vector);
			
		}	while(LinkedDocumentCollection.maxDiffInVector(oldVec, vector) > this.delta);
		
		return vector;
	}
	
	private static double maxDiffInVector(double[] vec1, double[] vec2)	{
		if ((vec1 == null) || (vec2 == null))	{
			return -1;
		}
		
		double diff = Math.abs(vec1[0] - vec2[0]);
		
		for (int i = 1; i < vec1.length; i++)	{
			if (diff > (vec1[i] - vec2[i]))	{
				diff = Math.abs(vec1[i] - vec2[i]);
			}
		}
		
		return diff;
	}
	
	private double[] sortByRelevance(double dampingFactor, double weightingFactor)	{
		
		return null;
	}
}
