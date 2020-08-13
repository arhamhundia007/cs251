/**
 * A Patient queue implementation using a dynamically-sized circular array.
 * 
 * @author TODO
 */
public class MyPatientQueue{
	// instance variables
	// declare instance variables
	// -----
    int head;
    int tail;
    Patient [] array;
    int size;
	// constructor
	public MyPatientQueue() {
		// initialize instance variables
		// -----
		this.array = new Patient[7];
		this.head = 0;
		this.tail = 0;
		this.size = 0;

	}

	// functions
	/**
	 * @return the number of patients in the queue
	 */
	public int size() {
		// return the number of patients in the queue
		return this.size;
		// -----
	}

	/**
	 * add patient to end of queue.
	 * @param p - Patient to add to queue
	 */
	public void enqueue(Patient p) {
		// add patient to end of queue
		// resize array, if needed
		// -----

		if (size == array.length) {
			Patient[] new_arr = new Patient[this.array.length * 2];
			for (int i = 0; i < size; i++) {
				new_arr[i] = this.array[(head+i) % (array.length)];
			}
			this.array = new_arr;
			head = 0;
			tail = size;
		}
		this.array[tail] = p;
		tail = ((tail + 1) % array.length);
		size++;
	}


	/**
	 * remove and return next patient from the queue
	 * @return patient at front of queue, null if queue is empty
	 */
	public Patient dequeue() {
		// remove and return the patient at the head of the queue
		// resize array, if needed


		if (size == 0){
			return null;
		}
		Patient temp;
		temp = this.array[head];
		this.array[head] = null;
		head = (head + 1) % this.array.length;
		size--;

		if (size <= (this.array.length / 4)){

			this.array = resize(size);
			head = 0;
			tail = size;
		}

		return temp;
	}

	/**
	 * return, but do not remove, the patient at index i
	 * @param i - index of patient to return
	 * @return patient at index i, or null if no such element
	 */
	public Patient get(int i) {
		// return, but do not remove, the patient at index i
		int index = (head + i) % this.array.length;

//		if (i > this.array.length - 1){
//			return null;
//		}
		return array[index];
		// -----
	}

	/**
	 * add patient to front of queue
	 * @param p - patient being added to queue
	 */
	public void push(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add Patient p to front of queue
		// resize array, if needed
		// -----
		if (size == array.length) {
			Patient[] new_arr = new Patient[this.array.length * 2];
			for (int i = 0; i < size; i++) {
				new_arr[i + 1] = this.array[(head + i) % (array.length)];
			}
			this.array = new_arr;
			head = 0;
			tail = size + 1;
		} else {
			if ((head - 1) < 0) {
				head = head + this.array.length - 1;
			} else {
				head = head - 1;
			}
		}
		this.array[head] = p;
		size++;

	}

	/**
	 * remove and return patient at index i from queue
	 * @param i - index of patient to remove
	 * @return patient at index i, null if no such element
	 */
	public Patient dequeue(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return Patient at index i from queue
		// shift patients down to fill hole left by removed patient
		// resize array, if needed
		if (size == 0){
			return null;
		}
		if (i < 0 || i >= this.array.length){
			return null;
		}

		if (this.array[(head + i) % this.array.length] == null){
			return null;
		}


		if (i == 0){
			return dequeue();
		}
		int index = (head + i) % this.array.length;

		Patient temp = this.array[index];
		this.array[index] = null;
		int counter = index;
		while(counter != tail){
			this.array[counter % this.array.length] = this.array[(counter+1) % this.array.length];
			counter = (counter + 1) % this.array.length;
		}
//		int j;
//
//		for (j = (head + i) % this.array.length ; j < (head + size - 1) % this.array.length; j++) {
//			this.array[j % this.array.length] = this.array[(j + 1) % this.array.length];
//		}


		if (tail == 0){
			tail = this.array.length - 1;
		} else {
			tail--;
		}
		this.array[tail] = null;

		size--;
		if (size <= (this.array.length / 4)){
			this.array = resize(size);
			head = 0;
			tail = size;
		}



		return temp;

		// -----
	}

	public Patient[] resize(int size){
		Patient[] new_arr;
		if (this.array.length / 4 < 7){
			new_arr = new Patient[7];
		} else {
			new_arr = new Patient[this.array.length / 2];
		}

		for(int i = 0; i < size; i++){
			new_arr[i] = this.array[(head + i) % this.array.length];
		}
		return  new_arr;
	}
}
