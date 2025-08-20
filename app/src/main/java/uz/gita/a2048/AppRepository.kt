package uz.gita.a2048

import kotlin.random.Random

class AppRepository {
    private val addElement = 2
    private var score = 0
    private var listener: ((Int) -> Unit)? = null

    fun setScoreListener(l: (Int)->Unit){
        listener = l
    }

    private var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        addNewElement()
        addNewElement()
    }

    fun setMatrix(matrix: Array<Array<Int>>) {
        if (checkMatrix(matrix)) {
            this.matrix = matrix
        }

    }

    fun score(): Int = score

    private fun checkMatrix(matrix: Array<Array<Int>>): Boolean {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (matrix[i][j] != 0) return true
            }
        }
        return false
    }

    fun setScore(score:Int){
        this.score = score
    }

    fun getMatrix(): Array<Array<Int>> {
        return matrix
    }

    private fun addNewElement() {
        val emptyPosList = ArrayList<Int>()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (matrix[i][j] == 0)
                    emptyPosList.add(4 * i + j)
            }
        }

        if (emptyPosList.size == 0) return
        val randomIndex = Random.nextInt(0, emptyPosList.size)


        matrix[emptyPosList[randomIndex] / 4][emptyPosList[randomIndex] % 4] = addElement

    }

    fun moveLeftDirection() {
        val newMatrix = createEmptyMatrix()
        val ls = ArrayList<Int>()
        var isAdded: Boolean
        for (i in 0 until 4) {
            isAdded = false
            ls.clear()
            for (j in 0 until 4) {
                if (matrix[i][j] == 0) continue
                if (ls.isEmpty()) {
                    ls.add(matrix[i][j])
                    continue
                }
                if (ls.last() == matrix[i][j] && !isAdded) {
                    ls[ls.size - 1] = 2 * ls.last()
                    score += ls.last()
                    isAdded = true
                } else {
                    ls.add(matrix[i][j])
                    isAdded = false
                }
            }
            for (k in 0 until ls.size)
                newMatrix[i][k] = ls[k]
        }
        matrix = newMatrix
        addNewElement()
        listener?.invoke(score)
    }

    fun moveRightDirection() {
        val newMatrix = createEmptyMatrix()
        val ls = ArrayList<Int>()
        var isAdded = false
        for (i in 0 until 4) {
            isAdded = false
            ls.clear()
            for (j in 3 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (ls.isEmpty()) {
                    ls.add(matrix[i][j])
                    continue
                }
                if (ls.last() == matrix[i][j] && !isAdded) {
                    ls[ls.size - 1] = 2 * ls.last()
                    score += ls.last()
                    isAdded = true
                } else {
                    ls.add(matrix[i][j])
                    isAdded = false
                }
            }
            for (k in ls.size - 1 downTo 0)
                newMatrix[i][3 - k] = ls[k]
        }
        matrix = newMatrix
        addNewElement()
        listener?.invoke(score)
    }

    fun moveUpDirection() {
        val newMatrix = createEmptyMatrix()
        val ls = ArrayList<Int>()
        var isAdded = false
        for (j in 0 until 4) {
            isAdded = false
            ls.clear()
            for (i in 0 until 4) {
                if (matrix[i][j] == 0) continue
                if (ls.isEmpty()) {
                    ls.add(matrix[i][j])
                    continue
                }
                if (ls.last() == matrix[i][j] && !isAdded) {
                    ls[ls.size - 1] = 2 * ls.last()
                    score += ls.last()
                    isAdded = true
                } else {
                    ls.add(matrix[i][j])
                    isAdded = false
                }
            }
            for (k in 0 until ls.size)
                newMatrix[k][j] = ls[k]
        }
        matrix = newMatrix
        addNewElement()
        listener?.invoke(score)
    }

    fun moveDownDirection() {
        val newMatrix = createEmptyMatrix()
        val ls = ArrayList<Int>()
        var isAdded = false
        for (j in 0 until 4) {
            isAdded = false
            ls.clear()
            for (i in 0 until 4) {
                if (matrix[i][j] == 0) continue
                if (ls.isEmpty()) {
                    ls.add(matrix[i][j])
                    continue
                }
                if (ls.last() == matrix[i][j] && !isAdded) {
                    ls[ls.size - 1] = 2 * ls.last()
                    score += ls.last()
                    isAdded = true
                } else {
                    ls.add(matrix[i][j])
                    isAdded = false
                }
            }
            for (k in ls.size - 1 downTo 0)
                newMatrix[3 - k][j] = ls[ls.size - 1 - k]
        }
        matrix = newMatrix
        addNewElement()
        listener?.invoke(score)
    }

    private fun createEmptyMatrix(): Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    fun checkFinish(): Boolean {
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, number ->
                if (number == 0) return true

                when {
                    j + 1 < 4 && number == matrix[i][j + 1] -> return true
                    j - 1 >= 0 && number == matrix[i][j - 1] -> return true
                    i + 1 < 4 && number == matrix[i + 1][j] -> return true
                    i - 1 >= 0 && number == matrix[i - 1][j] -> return true
                }
            }
        }
        return false
    }

    fun restart() {
        matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        addNewElement()
        addNewElement()
        score = 0
    }

}
