import { FmSizing } from "./types"

type Props = { sizing?: FmSizing; onChangeSize: (size: number) => void }

const sizeOptions = [10, 20, 50]

export default function FmTableSize({ sizing = { size: 10 }, onChangeSize }: Props) {
  const { total = 0, size } = sizing
  return (
    <div className="flex justify-between items-center">
      <span className="text-default-400 text-small">Total {total} rows</span>
      <label className="flex items-center text-default-400 text-small">
        Rows per page:
        <select
          className="bg-transparent outline-none text-default-400 text-small"
          defaultValue={10}
          onChange={(e) => onChangeSize(Number(e.target.value))}
        >
          {sizeOptions.map((sizeOption) => (
            <option key={sizeOption} value={sizeOption}>
              {sizeOption}
            </option>
          ))}
        </select>
      </label>
    </div>
  )
}
